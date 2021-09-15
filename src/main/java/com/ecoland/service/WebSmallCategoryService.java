package com.ecoland.service;

import com.ecoland.common.CommonService;
import com.ecoland.common.Constants;
import com.ecoland.entity.WebLargeCategories;
import com.ecoland.entity.WebSmallCategories;
import com.ecoland.exception.RecordNotFoundException;
import com.ecoland.model.ApiResponse;
import com.ecoland.model.ResultPageResponse;
import com.ecoland.model.request.EditOrCreateWebSmallCateRequest;
import com.ecoland.model.request.WebSmallCategoryRequest;
import com.ecoland.model.response.IdValueResponse;
import com.ecoland.repository.CommonDAO;
import com.ecoland.repository.WebLargeCategoriesRepository;
import com.ecoland.repository.WebSmallCategoriesRepository;
import com.ecoland.repository.dao.SmallCategoryDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WebSmallCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(WebSmallCategoryService.class);

    @Autowired
    private WebSmallCategoriesRepository webSmallCategoryRepository;

    @Autowired
    private SmallCategoryDao smallCategoryDao;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CommonDAO commonDAO;

    @Autowired
    private WebLargeCategoriesRepository webLargeCategoriesRepository;

    @CacheEvict(value = "smallCategoryListWithLargeSearch", allEntries = true)
    public ResultPageResponse getSmallCategory(WebSmallCategoryRequest request) {
        return smallCategoryDao.getSmallCategory(request);

    }

    public List<IdValueResponse> getLargeCategory() {
        return smallCategoryDao.getLargeCategory();

    }

    public void delete(Integer id) {
        logger.info("-- Delete web small category by id --");
        Optional<WebSmallCategories> item = webSmallCategoryRepository.findById(id);
        if (item.isEmpty()) {
            throw new RecordNotFoundException(Constants.RECORD_NOT_FOUND);
        }
        commonDAO.sqlCommonDAO(Constants.WEB_SMALL_CATEGORIES, Long.valueOf(id), Constants.DELETE,
                commonService.idUserAccountLogin());

    }

    public WebSmallCategories detail(Integer id) {
        logger.info("-- Detail web small category by id --");
        Optional<WebSmallCategories> optionalUserAccounts = webSmallCategoryRepository.findById(id);
        return optionalUserAccounts.get();
    }

    public ApiResponse edit(EditOrCreateWebSmallCateRequest request) throws Exception {
        logger.info("-- Edit web small category  --");
        Optional<WebSmallCategories> optionalWebSmallCategories = webSmallCategoryRepository.findById(request.getId());
        Optional<WebSmallCategories> optionalWebSmallCategory = webSmallCategoryRepository
                .findByCategoryName(request.getCategoryName().trim());
        if (optionalWebSmallCategories.isEmpty() || optionalWebSmallCategories.get().getDeleteFlag() != 0) {
            logger.error(Constants.RECORD_NOT_FOUND);
            throw new Exception(Constants.RECORD_NOT_FOUND);
        }
        Optional<WebLargeCategories> optionalWebLargeCategories = webLargeCategoriesRepository
                .findByIdAndDeleteFlag(request.getLargeCategoryId(), 0);
        if (optionalWebLargeCategories.isEmpty()) {
            throw new Exception(Constants.RECORD_NOT_FOUND);
        }
        if (!optionalWebSmallCategory.isEmpty()
                && optionalWebSmallCategory.get().getId().compareTo(request.getId()) != 0) {
            logger.error("-- Edit web small category by category name error --");
            return new ApiResponse(Constants.HTTP_CODE_403, Constants.RECORD_ALREADY_EXISTS, null);
        }
        optionalWebSmallCategories.get().setWebLargeCategories(optionalWebLargeCategories.get());
        optionalWebSmallCategories.get().setCategoryName(request.getCategoryName());
        webSmallCategoryRepository.save(optionalWebSmallCategories.get());
        commonDAO.sqlCommonDAO(Constants.WEB_SMALL_CATEGORIES, Long.valueOf(request.getId()), Constants.UPDATE,
                commonService.idUserAccountLogin());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }

    public ApiResponse create(EditOrCreateWebSmallCateRequest request) throws Exception {
        logger.info("-- Create web small category by request --");
        WebSmallCategories webSmallCategories = new WebSmallCategories();
        Optional<WebLargeCategories> optionalWebLargeCategories = webLargeCategoriesRepository
                .findByIdAndDeleteFlag(request.getLargeCategoryId(), 0);
        Optional<WebSmallCategories> optionalWebSmallCategories = webSmallCategoryRepository
                .findByCategoryName(request.getCategoryName().trim());
        if (optionalWebLargeCategories.isEmpty()) {
            throw new Exception(Constants.RECORD_NOT_FOUND);
        }
        if (!optionalWebSmallCategories.isEmpty()) {
            logger.error("-- Create web small category by category name error --");
            return new ApiResponse(Constants.HTTP_CODE_403, Constants.RECORD_ALREADY_EXISTS, null);
        }
        webSmallCategories.setWebLargeCategories(optionalWebLargeCategories.get());
        webSmallCategories.setCategoryName(request.getCategoryName());
        webSmallCategories.setCreatedBy(commonService.idUserAccountLogin());
        webSmallCategories.setCreateAt(new Timestamp(new Date().getTime()));
        webSmallCategories.setUpdatedBy(commonService.idUserAccountLogin());
        webSmallCategories.setUpdatedAt(new Timestamp(new Date().getTime()));
        webSmallCategories.setDeleteFlag(Constants.DELETE_NONE);
        webSmallCategoryRepository.save(webSmallCategories);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }

}
