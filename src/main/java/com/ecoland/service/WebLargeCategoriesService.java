package com.ecoland.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecoland.common.CommonService;
import com.ecoland.common.Constants;
import com.ecoland.entity.WebLargeCategories;
import com.ecoland.exception.RecordNotFoundException;
import com.ecoland.model.ApiResponse;
import com.ecoland.model.ResultPageResponse;
import com.ecoland.model.request.system.EditOrCreateWebLargeCategoryRequest;
import com.ecoland.model.request.system.SearchWebLargeCategoryRequest;
import com.ecoland.model.response.system.SearchWebLargeCategoryResponse;
import com.ecoland.repository.CommonDAO;
import com.ecoland.repository.WebLargeCategoriesRepository;
import com.ecoland.repository.dao.WebLargeCategoriesDAO;

/**
 * 
 * @author Tien-ITS
 * 
 */
@Service
@Transactional
public class WebLargeCategoriesService {
    private static final Logger logger = LoggerFactory.getLogger(WebLargeCategoriesService.class);

    @Autowired
    private WebLargeCategoriesRepository webLargeCategoriesRepository;

    @Autowired
    private WebLargeCategoriesDAO webLargeCategoriesDAO;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CommonDAO commonDAO;

    /**
     * 
     * @author Tien-ITS
     * @return webLargeCategories
     */
    @Cacheable(cacheNames = "listWebLargeCategories")
    public List<WebLargeCategories> getListWebLargeCategories() {
        logger.info("<-- get list webLargeCategory -->");
        return webLargeCategoriesRepository.findAll();
    }

    /**
     * @author Tien-ITS
     * 
     * @param SearchWebLargeCategoryRequest Web Large Category search request
     * 
     * @return ResultPageResponse response of Web Large Category search
     */
    public ResultPageResponse searchWebLargeCategory(SearchWebLargeCategoryRequest request) {
        List<SearchWebLargeCategoryResponse> dataProvider = webLargeCategoriesDAO.getWebLargeCategoryList(request);
        int totalRecord = this.webLargeCategoriesDAO.getToTalRecord(request);
        ResultPageResponse response = new ResultPageResponse(totalRecord, dataProvider, null, request.getPageNo());
        return response;
    }

    /**
     * @author Tien-ITS
     * 
     * @summary get detail WebLargeCategory
     * 
     * @param request
     */
    public SearchWebLargeCategoryResponse getWebLargeCategoryDetail(int id) {
        Optional<WebLargeCategories> opItem = webLargeCategoriesRepository.findByIdAndDeleteFlag(id,
                Constants.DELETE_NONE);
        if (opItem.isEmpty()) {
            throw new RecordNotFoundException(Constants.RECORD_DOES_NOT_EXIST);
        }
        WebLargeCategories categories = opItem.get();
        SearchWebLargeCategoryResponse res = new SearchWebLargeCategoryResponse();
        res.setId(categories.getId());
        res.setCategoryName(categories.getCategoryName());
        return res;
    }

    /**
     * @author Tien-ITS
     * 
     * @summary create WebLargeCategory
     * 
     * @param request
     */
    public ApiResponse createWebLargeCategory(EditOrCreateWebLargeCategoryRequest request) throws Exception {
        logger.info("-- Create web large category --");
        Optional<WebLargeCategories> lstWebLargeCategory = webLargeCategoriesRepository
                .findByCategoryName(request.getCategoryName().trim());
        Integer idLogin = commonService.idUserAccountLogin();
        if (!lstWebLargeCategory.isEmpty()) {
            logger.error(Constants.RECORD_ALREADY_EXISTS);
            return new ApiResponse(Constants.HTTP_CODE_403, Constants.RECORD_ALREADY_EXISTS, null);
        } else {
            WebLargeCategories categories = new WebLargeCategories();
            categories.setCategoryName(request.getCategoryName().trim());
            categories.setCreatedBy(idLogin);
            categories.setUpdatedBy(idLogin);
            categories.setDeleteFlag(Constants.DELETE_NONE);
            categories.setCreateAt(new Timestamp(new Date().getTime()));
            categories.setUpdatedAt(new Timestamp(new Date().getTime()));
            webLargeCategoriesRepository.save(categories);
            return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
        }
    }

    /**
     * @author Tien-ITS
     * 
     * @summary edit WebLargeCategory
     * 
     * @param request
     */
    public ApiResponse editWebLargeCategory(EditOrCreateWebLargeCategoryRequest request) throws Exception {
        logger.info("-- Edit web large categories --");
        Optional<WebLargeCategories> optWebLargeCategories = webLargeCategoriesRepository.findById(request.getId());
        if (optWebLargeCategories.isEmpty()) {
            logger.error(Constants.RECORD_DOES_NOT_EXIST);
            throw new Exception(Constants.RECORD_DOES_NOT_EXIST);
        }
        Optional<WebLargeCategories> webLargeCategory = webLargeCategoriesRepository
                .findByCategoryName(request.getCategoryName().trim());
        if (!webLargeCategory.isEmpty() && webLargeCategory.get().getId().compareTo(request.getId()) != 0) {
            logger.error(Constants.RECORD_ALREADY_EXISTS);
            return new ApiResponse(Constants.HTTP_CODE_403, Constants.RECORD_ALREADY_EXISTS, null);
        }
        WebLargeCategories categories = optWebLargeCategories.get();
        categories.setCategoryName(request.getCategoryName().trim());
        webLargeCategoriesRepository.save(categories);
        commonDAO.sqlCommonDAO(Constants.WEB_LARGE_CATEGORIES, Long.valueOf(request.getId()), Constants.UPDATE,
                commonService.idUserAccountLogin());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }

    /**
     * @author Tien-ITS
     * 
     * @summary delete-soft WebLargeCategory
     * 
     * @param id WebLargeCategory
     */
    public void deleteWebLargeCategory(int id) {
        logger.info("-- Delete web large categories by id --");
        Optional<WebLargeCategories> opItem = webLargeCategoriesRepository.findByIdAndDeleteFlag(id,
                Constants.DELETE_NONE);
        if (opItem.isEmpty()) {
            throw new RecordNotFoundException(Constants.RECORD_NOT_FOUND);
        }
        WebLargeCategories delItem = opItem.get();
        webLargeCategoriesRepository.save(delItem);
        commonDAO.sqlCommonDAO(Constants.WEB_LARGE_CATEGORIES, Long.valueOf(id), Constants.DELETE,
                commonService.idUserAccountLogin());
    }
}
