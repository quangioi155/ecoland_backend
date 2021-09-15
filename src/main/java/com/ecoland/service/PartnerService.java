package com.ecoland.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.ecoland.common.CommonService;
import com.ecoland.common.Constants;
import com.ecoland.entity.Partners;
import com.ecoland.exception.RecordNotFoundException;
import com.ecoland.model.ResultPageResponse;
import com.ecoland.model.request.system.FormCompanyRequest;
import com.ecoland.model.request.system.SearchCompanyRequest;
import com.ecoland.model.response.DropdownListResponse;
import com.ecoland.model.response.system.FormCompanyResponse;
import com.ecoland.model.response.system.SearchCompanyResponse;
import com.ecoland.repository.PartnersRepository;
import com.ecoland.repository.UserAccountsRepository;
import com.ecoland.repository.dao.CompanyGroupDao;
import com.ecoland.utils.Utils;

/**
 * @class CompanyGroupService
 * 
 * @Service of partner function
 * 
 * @author thaotv@its-global.vn
 * @update ITSG-HoanNNC
 */
@Service
public class PartnerService {
    private static final Logger logger = LoggerFactory.getLogger(PartnerService.class);

    @Autowired
    private PartnersRepository partnersRepository;

    @Autowired
    private CompanyGroupDao companyDao;

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserAccountsRepository accountReposistory;

    /**
     * 
     * @author thaotv@its-global.vn
     * @return partners
     */
    @Cacheable(cacheNames = "listPartners")
    public List<Partners> getListPartners() {
        logger.info("<-- get list partner -->");
        return partnersRepository.findAll();
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @return partners drop down list
     */
    @Cacheable(cacheNames = "partnersDropdownList")
    public List<DropdownListResponse> partnersDropdownList() {
        logger.info("<-- Get information dropdown list partners -->");
        return partnersRepository.partnersDropdownList();
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @return optional Partners
     */
    public Optional<Partners> findByIdAndDeleteFlagPartners(Integer id, Integer deleteFlag) {
        return partnersRepository.findByIdAndDeleteFlag(id, deleteFlag);
    }

    /**
     * @author ITSG - HoanNNC
     * 
     * @param SearchCompanyRequest company group search request
     * @return ResultPageResponse response of company group search
     */
    public ResultPageResponse searchCompanyGroup(SearchCompanyRequest request) {
        List<SearchCompanyResponse> dataProvider = companyDao.getCompanyGroupList(request);
        int totalRecord = this.companyDao.getToTalRecord(request);
        ResultPageResponse response = new ResultPageResponse(totalRecord, dataProvider, null, request.getPageNo());
        return response;

    }

    /**
     * @author ITSG - HoanNNC.
     * 
     * @summary delete-soft partner
     * 
     * @param id partner
     */
    public void deletePartner(int id) {
        Optional<Partners> opItem = partnersRepository.findByIdAndDeleteFlag(id, Constants.DELETE_NONE);
        if (opItem.isEmpty()) {
            throw new RecordNotFoundException("record not found");
        }
        Partners delItem = opItem.get();
        if (accountReposistory.countAccountByPartnerId(delItem.getId()) > 0) {
            throw new RecordNotFoundException("item is refer of user_aacounts");
        }
        delItem.setDeleteFlag(Constants.DELETE_TRUE);
        partnersRepository.save(delItem);
    }

    /**
     * @author ITSG - HoanNNC.
     * 
     * @summary update/regist partner
     * 
     * @param FormCompanyRequest req
     * @return partner id
     */
    public int onChangePartner(FormCompanyRequest req) {
        if (req.getId() != null) {
            return updatePartner(req);
        } else {
            return registPartner(req);
        }
    }

    /**
     * @author ITSG - HoanNNC.
     * 
     * @summary update partner
     * 
     * @param FormCompanyRequest req
     * @return partner id
     */
    private int updatePartner(FormCompanyRequest req) {
        logger.info("-- Edit partner --");
        Optional<Partners> opItem = partnersRepository.findByIdAndDeleteFlag(req.getId(), Constants.DELETE_NONE);
        if (opItem.isEmpty()) {
            throw new RecordNotFoundException(Constants.RECORD_NOT_FOUND);
        }
        Partners partner = opItem.get();
        // check duplcate edit name
        Long count = partnersRepository.countByPartnerNameAndDeleteFlag(req.getPartnerName().trim(),
                Constants.DELETE_NONE);
        if (count > 0 && !StringUtils.trimToEmpty(partner.getPartnerName())
                .equals(StringUtils.trimToEmpty(req.getPartnerName()))) {
            throw new DuplicateKeyException(Constants.RECORD_ALREADY_EXISTS);
        }
        partner.setPartnerName(StringUtils.trimToEmpty(req.getPartnerName()));
        partner.setAddress1(StringUtils.trimToEmpty(req.getAddress1()));
        partner.setAddress2(StringUtils.trimToEmpty(req.getAddress2()));
        partner.setAddress3(StringUtils.trimToEmpty(req.getAddress3()));
        partner.setPostalCode(StringUtils.trimToEmpty(req.getPostalCode()));
        partner.setTel(StringUtils.trimToEmpty(req.getTel()));
        partner.setFax(StringUtils.trimToEmpty(req.getFax()));
        partner.setMailAddress(StringUtils.trimToEmpty(req.getMailAddress()));
        if (StringUtils.isNotBlank(req.getStartDate())) {
            Date startDate = Utils.convertStringToDate("yyyy/MM/dd", req.getStartDate());
            partner.setStartDate(startDate);
        }
        if (StringUtils.isNotBlank(req.getEndDate())) {
            Date endDate = Utils.convertStringToDate("yyyy/MM/dd", req.getEndDate());
            partner.setEndDate(endDate);
        }
        partner.setManagerName(StringUtils.trimToEmpty(req.getManagerName()));
        partner.setMainFlag(req.getMainFlag());
        partner.setUpdatedAt(new Timestamp(new Date().getTime()));
        partner.setUpdatedBy(commonService.idUserAccountLogin());

        return partnersRepository.save(partner).getId();
    }

    /**
     * @author ITSG - HoanNNC.
     * 
     * @summary regist partner
     * 
     * @param FormCompanyRequest req
     * @return partner id
     */
    public int registPartner(FormCompanyRequest req) {
        logger.info("-- Create partner --");
        if (partnersRepository.countByPartnerNameAndDeleteFlag(req.getPartnerName(), Constants.DELETE_NONE) > 0) {
            throw new DuplicateKeyException(Constants.RECORD_ALREADY_EXISTS);
        }
        Integer idLogin = commonService.idUserAccountLogin();
        Partners partner = new Partners();
        partner.setPartnerName(StringUtils.trimToEmpty(req.getPartnerName()));
        partner.setAddress1(StringUtils.trimToEmpty(req.getAddress1()));
        partner.setAddress2(StringUtils.trimToEmpty(req.getAddress2()));
        partner.setAddress3(StringUtils.trimToEmpty(req.getAddress3()));
        partner.setPostalCode(StringUtils.trimToEmpty(req.getPostalCode()));
        partner.setTel(StringUtils.trimToEmpty(req.getTel()));
        partner.setFax(StringUtils.trimToEmpty(req.getFax()));
        partner.setMailAddress(StringUtils.trimToEmpty(req.getMailAddress()));
        if (StringUtils.isNotBlank(req.getStartDate())) {
            Date startDate = Utils.convertStringToDate("yyyy/MM/dd", req.getStartDate());
            partner.setStartDate(startDate);
        }
        if (StringUtils.isNotBlank(req.getEndDate())) {
            Date endDate = Utils.convertStringToDate("yyyy/MM/dd", req.getEndDate());
            partner.setEndDate(endDate);
        }
        partner.setManagerName(StringUtils.trimToEmpty(req.getManagerName()));
        partner.setMainFlag(req.getMainFlag());
        partner.setCreateAt(new Timestamp(new Date().getTime()));
        partner.setUpdatedAt(new Timestamp(new Date().getTime()));
        partner.setCreatedBy(idLogin);
        partner.setUpdatedBy(0);
        partner.setDeleteFlag(0);

        return partnersRepository.save(partner).getId();
    }

    public FormCompanyResponse getPartnerDetail(int id) {
        Optional<Partners> opItem = partnersRepository.findByIdAndDeleteFlag(id, Constants.DELETE_NONE);
        if (opItem.isEmpty()) {
            throw new RecordNotFoundException("record not found");
        }
        Partners partner = opItem.get();
        FormCompanyResponse res = new FormCompanyResponse();
        res.setId(partner.getId());
        res.setPartnerName(partner.getPartnerName());
        res.setAddress1(partner.getAddress1());
        res.setAddress2(partner.getAddress2());
        res.setAddress3(partner.getAddress3());
        res.setPostalCode(partner.getPostalCode());
        res.setTel(partner.getTel());
        res.setFax(partner.getFax());
        res.setMailAddress(partner.getMailAddress());
        String startDate = "";
        String endDate = "";
        if (partner.getStartDate() != null) {
            startDate = Utils.convertDateToString("yyyy/MM/dd", partner.getStartDate());
        }
        if (partner.getEndDate() != null) {
            endDate = Utils.convertDateToString("yyyy/MM/dd", partner.getEndDate());
        }
        res.setStartDate(startDate);
        res.setEndDate(endDate);
        res.setManagerName(partner.getManagerName());
        res.setMainFlag(partner.getMainFlag());

        return res;

    }
}
