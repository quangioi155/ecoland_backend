package com.ecoland.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecoland.common.CommonService;
import com.ecoland.common.Constants;
import com.ecoland.entity.Branches;
import com.ecoland.entity.BranchesLocations;
import com.ecoland.entity.Estimates;
import com.ecoland.entity.GetStockCodes;
import com.ecoland.entity.MainSystemProjects;
import com.ecoland.entity.MainSystemouts;
import com.ecoland.entity.Partners;
import com.ecoland.entity.Sales;
import com.ecoland.entity.UserAccounts;
import com.ecoland.model.ApiResponse;
import com.ecoland.model.ResultPageResponse;
import com.ecoland.model.request.system.EditOrCreateBranchRequest;
import com.ecoland.model.request.system.SearchBranchRequest;
import com.ecoland.model.response.DropdownListResponse;
import com.ecoland.model.response.system.DetailBranchResponse;
import com.ecoland.repository.BranchesLocationsRepository;
import com.ecoland.repository.BranchesRepository;
import com.ecoland.repository.CommonDAO;
import com.ecoland.repository.EstimatesRepository;
import com.ecoland.repository.GetStockCodesRepository;
import com.ecoland.repository.MainSystemProjectsRepository;
import com.ecoland.repository.MainSystemoutsRepository;
import com.ecoland.repository.SalesRepository;
import com.ecoland.repository.UserAccountsRepository;
import com.ecoland.repository.dao.BranchDAO;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Service
@Transactional
public class BranchesService {
    private static final Logger logger = LoggerFactory.getLogger(BranchesService.class);

    @Autowired
    private BranchesRepository branchesRepository;

    @Autowired
    private UserAccountsRepository userAccountsRepository;

    @Autowired
    private BranchesLocationsRepository branchesLocationsRepository;

    @Autowired
    private EstimatesRepository estimatesRepository;

    @Autowired
    private GetStockCodesRepository getStockCodesRepository;

    @Autowired
    private MainSystemoutsRepository mainSystemoutsRepository;

    @Autowired
    private MainSystemProjectsRepository mainSystemProjectsRepository;

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private BranchDAO branchDAO;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CommonDAO commonDAO;

    /**
     * 
     * @author thaotv@its-global.vn
     * @return list branches
     * 
     */
    @Cacheable(cacheNames = "listBranches")
    public List<Branches> getListBranches() {
        logger.info("-- Get all branches --");
        return branchesRepository.findAll();
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @return drop down list
     * 
     */
    @Cacheable(cacheNames = "branchesDropdownList")
    public List<DropdownListResponse> branchesDropdownList() {
        logger.info("-- Get infomation dropdown list branches --");
        return branchesRepository.branchesDropdownList();
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @return drop down list
     * 
     */
    @Cacheable(cacheNames = "branchesByPartnerId")
    public List<DropdownListResponse> brancheByPartnerId(Integer id){
        logger.info("-- Get infomation dropdown list branches by partner Id --");
        return branchesRepository.branchesByPartnerId(id);
    }
    /**
     * 
     * @author thaotv@its-global.vn
     * @return optional branches
     * 
     */
    public Optional<Branches> findByIdAndDeleteFlagBranches(Integer id, Integer deleteFlag) {
        logger.info("-- Get information branches find by id and delete flag --");
        return branchesRepository.findByIdAndDeleteFlag(id, deleteFlag);
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @param request
     * @return result page response
     * 
     */
    @CacheEvict(value = "userGroupsSearch", allEntries = true)
    public ResultPageResponse searchBranch(SearchBranchRequest request) {
        logger.info("-- Get information branch --");
        return branchDAO.searchBranch(request);
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @param id
     * @return update delete flag = 1
     * 
     */
    public void deleteBranchById(Integer id) throws Exception {
        logger.info("-- Delete branch by id --");
        Optional<Branches> optionalBranches = findByIdAndDeleteFlagBranches(id, 0);
        if (optionalBranches.isEmpty()) {
            logger.error(Constants.RECORD_NOT_FOUND);
            throw new Exception(Constants.RECORD_NOT_FOUND);
        } else {
            // check branch exist in table user accounts,branch_locatiion,main
            // systemouts,sales,main_system_project, get stock code,estimate
            List<UserAccounts> userAccounts = userAccountsRepository.findByBranchIdAndDeleteFlag(id,
                    Constants.DELETE_NONE);
            List<BranchesLocations> branchesLocations = branchesLocationsRepository.findByBranchIdAndDeleteFlag(id,
                    Constants.DELETE_NONE);
            List<MainSystemouts> mainSystemouts = mainSystemoutsRepository.findByBranchIdAndDeleteFlag(id,
                    Constants.DELETE_NONE);
            List<MainSystemProjects> mainSystemProjects = mainSystemProjectsRepository.findByBranchIdAndDeleteFlag(id,
                    Constants.DELETE_NONE);
            List<Sales> sales = salesRepository.findByBranchIdAndDeleteFlag(id, Constants.DELETE_NONE);
            List<GetStockCodes> getStockCodes = getStockCodesRepository.findByBranchIdAndDeleteFlag(id,
                    Constants.DELETE_NONE);
            List<Estimates> estimates = estimatesRepository.findByBranchIdAndDeleteFlag(id, Constants.DELETE_NONE);

            if (!userAccounts.isEmpty() || !branchesLocations.isEmpty() || !mainSystemouts.isEmpty()
                    || !mainSystemProjects.isEmpty() || !sales.isEmpty() || !getStockCodes.isEmpty()
                    || !estimates.isEmpty()) {
                logger.error(Constants.RECORD_ALREADY_EXISTS);
                throw new Exception(Constants.RECORD_ALREADY_EXISTS);
            } else {
                commonDAO.sqlCommonDAO(Constants.BRANCHES, Long.valueOf(id), Constants.DELETE,
                        commonService.idUserAccountLogin());
            }
        }
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @param id
     * @return detail branch response
     * 
     */
    public DetailBranchResponse detailBranchById(Integer id) throws Exception {
        logger.info("-- Get infomation detail branch by id --");
        DetailBranchResponse detailBranchResponse = new DetailBranchResponse();
        Optional<Branches> optionalBranch = branchesRepository.findByIdAndDeleteFlag(id, Constants.DELETE_NONE);
        if (optionalBranch.isEmpty()) {
            logger.error(Constants.RECORD_NOT_FOUND);
            throw new Exception(Constants.RECORD_NOT_FOUND);
        } else {
            detailBranchResponse.setId(optionalBranch.get().getId());
            detailBranchResponse.setPartnerId(optionalBranch.get().getPartners().getId());
            detailBranchResponse.setBranchName(optionalBranch.get().getBranchName());
            detailBranchResponse.setBranchShortName(optionalBranch.get().getBranchShortName());
            detailBranchResponse.setAddress1(optionalBranch.get().getAddress1());
            detailBranchResponse.setAddress2(optionalBranch.get().getAddress2());
            detailBranchResponse.setAddress3(optionalBranch.get().getAddress3());
            detailBranchResponse.setTel(optionalBranch.get().getTel());
            detailBranchResponse.setFax(optionalBranch.get().getFax());
            detailBranchResponse.setStartDate(optionalBranch.get().getStartDate());
            detailBranchResponse.setEndDate(optionalBranch.get().getEndDate());
            detailBranchResponse.setInputCorpSite(optionalBranch.get().getInputCorpSite());
            detailBranchResponse.setDelivCorpSite(optionalBranch.get().getDelivCorpSite());
            detailBranchResponse.setPostalCode(optionalBranch.get().getPostalCode());
        }
        return detailBranchResponse;
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @param request
     * @return ApiResponse
     * 
     */
    public ApiResponse createBranch(EditOrCreateBranchRequest request) throws Exception {
        logger.info("-- Create branch --");
        Integer idLogin = commonService.idUserAccountLogin();
        Optional<Partners> optionalPartners = partnerService.findByIdAndDeleteFlagPartners(request.getPartnerId(),
                Constants.DELETE_NONE);
        if (optionalPartners.isEmpty()) {
            logger.error(Constants.RECORD_NOT_FOUND);
            throw new Exception(Constants.RECORD_NOT_FOUND);
        }
        Branches branch = new Branches();
        setBranch(branch, optionalPartners, request);
        branch.setCreateAt(new Timestamp(new Date().getTime()));
        branch.setUpdatedAt(new Timestamp(new Date().getTime()));
        branch.setCreatedBy(idLogin);
        branch.setUpdatedBy(idLogin);
        branch.setDeleteFlag(0);
        branchesRepository.save(branch);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @param request
     * @return ApiResponse
     * 
     */
    public ApiResponse editBranch(EditOrCreateBranchRequest request) throws Exception {
        logger.info("-- Edit branch by id --");
        Optional<Partners> optionalPartners = partnerService.findByIdAndDeleteFlagPartners(request.getPartnerId(),
                Constants.DELETE_NONE);
        Optional<Branches> optionalBranch = findByIdAndDeleteFlagBranches(request.getId(), Constants.DELETE_NONE);
        if (optionalPartners.isEmpty() || optionalBranch.isEmpty()) {
            logger.error(Constants.RECORD_NOT_FOUND);
            throw new Exception(Constants.RECORD_NOT_FOUND);
        }
        setBranch(optionalBranch.get(), optionalPartners, request);
        branchesRepository.save(optionalBranch.get());
        commonDAO.sqlCommonDAO(Constants.BRANCHES, Long.valueOf(request.getId()), Constants.UPDATE,
                commonService.idUserAccountLogin());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }

    /**
     * set branch
     * @author thaotv@its-global.vn
     * 
     */
    public void setBranch(Branches branch, Optional<Partners> optionalPartners, EditOrCreateBranchRequest request) {
        branch.setPartners(optionalPartners.get());
        branch.setBranchName(request.getBranchName().trim());
        branch.setBranchShortName(request.getBranchShortName().trim());
        branch.setPostalCode(request.getPostalCode().trim());
        branch.setAddress1(request.getAddress1().trim());
        branch.setAddress2(request.getAddress2().trim());
        branch.setAddress3(request.getAddress3().trim());
        branch.setTel(request.getTel().trim());
        branch.setFax(request.getFax().trim());
        branch.setStartDate(request.getStartDate());
        branch.setEndDate(request.getEndDate());
        branch.setInputCorpSite(request.getInputCorpSite());
        branch.setDelivCorpSite(request.getDelivCorpSite());
    }
}
