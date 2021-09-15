package com.ecoland.service;

import com.ecoland.common.CommonService;
import com.ecoland.common.Constants;
import com.ecoland.entity.OutsourceCompanies;
import com.ecoland.entity.Partners;
import com.ecoland.exception.RecordNotFoundException;
import com.ecoland.model.ApiResponse;
import com.ecoland.model.ResultPageResponse;
import com.ecoland.model.request.system.EditOrCreateOutsourceCompanyRequest;
import com.ecoland.model.request.system.SearchOutsourceCompanyRequest;
import com.ecoland.repository.CommonDAO;
import com.ecoland.repository.OutsourceCompanyRepository;
import com.ecoland.repository.PartnersRepository;
import com.ecoland.repository.dao.OutsourceCompanyDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
/**
 * 
 * @author quannn@its-global.vn
 * 
 */
@Service
@Transactional
public class OutsourceCompanyService {

    private static final Logger logger = LoggerFactory.getLogger(OutsourceCompanyService.class);

    @Autowired
    private OutsourceCompanyRepository outsourceCompanyRepository;

    @Autowired
    private PartnersRepository partnersRepository;

    @Autowired
    private OutsourceCompanyDAO outsourceCompanyDAO;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CommonDAO commonDAO;

    public Optional<OutsourceCompanies> findById(Integer id) {
        logger.info("-- Get information product category by id --");
        return outsourceCompanyRepository.findById(id);
    }

    @CacheEvict(value = "userGroupsSearch", allEntries = true)
    public ResultPageResponse searchOutSourceCompany(SearchOutsourceCompanyRequest request) {
        logger.info("-- Search information outsource company --");
        return outsourceCompanyDAO.searchOutsourceCompany(request);
    }

    public void deleteOutsourceCompany(int id) {
        Optional<OutsourceCompanies> opItem = outsourceCompanyRepository.findByIdAndDeleteFlag(id, Constants.DELETE_NONE);
        if (opItem.isEmpty()) {
            throw new RecordNotFoundException("record not found");
        }
        OutsourceCompanies delItem = opItem.get();
        delItem.setDeleteFlag(Constants.DELETE_TRUE);
        outsourceCompanyRepository.save(delItem);
    }

    public ApiResponse createProductCategory(EditOrCreateOutsourceCompanyRequest request) throws Exception {
        logger.info("-- Create outsource company --");
        Integer idLogin = commonService.idUserAccountLogin();
        OutsourceCompanies outsourceCompany = new OutsourceCompanies();
        setOutsourceCompany(outsourceCompany, request);
        outsourceCompany.setCreateAt(new Timestamp(new Date().getTime()));
        outsourceCompany.setUpdatedAt(new Timestamp(new Date().getTime()));
        outsourceCompany.setCreatedBy(idLogin);
        outsourceCompany.setUpdatedBy(idLogin);
        outsourceCompany.setDeleteFlag(0);
        outsourceCompanyRepository.save(outsourceCompany);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }

    public ApiResponse editProductCategory(EditOrCreateOutsourceCompanyRequest request) throws Exception {
        logger.info("-- Edit outsource company by id --");
        Optional<OutsourceCompanies> optionalOutsourceCompanies = findById(request.getId());
        if (optionalOutsourceCompanies.isEmpty()) {
            logger.error(Constants.RECORD_NOT_FOUND);
            throw new Exception(Constants.RECORD_NOT_FOUND);
        }
        setOutsourceCompany(optionalOutsourceCompanies.get(), request);
        outsourceCompanyRepository.save(optionalOutsourceCompanies.get());
        commonDAO.sqlCommonDAO(Constants.PRODUCT_CATEGORIES, Long.valueOf(request.getId()), Constants.UPDATE,
                commonService.idUserAccountLogin());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }

    public void setOutsourceCompany(OutsourceCompanies outsourceCompany, EditOrCreateOutsourceCompanyRequest request) {
        Optional<Partners> partners = Optional.empty();

        if (request.getPartnerName() != null) {
            partners = partnersRepository.findByPartnerName(request.getPartnerName());
        }

        outsourceCompany.setPartners(partners.orElse(null));
        outsourceCompany.setOutsourceCompanyName(request.getName().trim());
        outsourceCompany.setOutsourceCompanyNameKana(request.getNameKana().trim());
        outsourceCompany.setOutsourceCompanyShortName(request.getShortName().trim());
        outsourceCompany.setPostalCode(request.getPostalCode().trim());
        outsourceCompany.setAddress1(request.getAddress1().trim());
        outsourceCompany.setAddress2(request.getAddress2().trim());
        outsourceCompany.setAddress3(request.getAddress3().trim());
        outsourceCompany.setTel(request.getTel().trim());
        outsourceCompany.setFax(request.getFax().trim());
    }
}
