package com.ecoland.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecoland.common.CommonService;
import com.ecoland.common.Constants;
import com.ecoland.entity.EstimateDetails;
import com.ecoland.entity.OemMappings;
import com.ecoland.entity.ProductCategory;
import com.ecoland.entity.ProductRanks;
import com.ecoland.entity.Stocks;
import com.ecoland.entity.WebSmallCategories;
import com.ecoland.model.ApiResponse;
import com.ecoland.model.ResultPageResponse;
import com.ecoland.model.request.RequestDTO;
import com.ecoland.model.request.system.EditOrCreateProductCategoryRequest;
import com.ecoland.model.response.system.DetailProductCategoryResponse;
import com.ecoland.repository.CommonDAO;
import com.ecoland.repository.EstimateDetailsRepository;
import com.ecoland.repository.OemMappingsRepository;
import com.ecoland.repository.ProductCategoryRepository;
import com.ecoland.repository.ProductRanksRepository;
import com.ecoland.repository.StocksRepository;
import com.ecoland.repository.WebSmallCategoriesRepository;
import com.ecoland.repository.dao.ProductCategoryDAO;

@Service
@Transactional
public class ProductCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryService.class);

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private OemMappingsRepository oemMappingsRepository;

    @Autowired
    private EstimateDetailsRepository estimateDetailsRepository;

    @Autowired
    private StocksRepository stocksRepository;

    @Autowired
    private ProductRanksRepository productRanksReposistory;

    @Autowired
    private WebSmallCategoriesRepository webSmallCategoriesRepository;

    @Autowired
    private ProductCategoryDAO productCategoryDAO;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CommonDAO commonDAO;

    public Optional<ProductCategory> findById(Integer id) {
        logger.info("-- Get information product category by id --");
        return productCategoryRepository.findById(id);

    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @param request
     * @return result page response
     * 
     */
    @CacheEvict(value = "productCategorySearch", allEntries = true)
    public ResultPageResponse searchProductCategory(RequestDTO requestDTO) {
        return productCategoryDAO.searchProductCategory(requestDTO);
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @param id
     * @return detail product category response
     */
    public DetailProductCategoryResponse detailProductCategory(Integer id) throws Exception {
        DetailProductCategoryResponse detailProductCategory = new DetailProductCategoryResponse();
        logger.info("-- Get information detail product category by id --");
        Optional<ProductCategory> optionalProductCategory = findById(id);
        if (optionalProductCategory.isEmpty()) {
            logger.error(Constants.RECORD_NOT_FOUND);
            throw new Exception(Constants.RECORD_NOT_FOUND);
        } else {
            detailProductCategory.setId(optionalProductCategory.get().getId());
            detailProductCategory.setCategoryName(optionalProductCategory.get().getCategoryName());
            detailProductCategory.setPickupFeeNoTax(optionalProductCategory.get().getPickupFeeNoTax());
            detailProductCategory.setWarewhousingFeeNoTax(optionalProductCategory.get().getWarehousingFeeNoTax());
            detailProductCategory
                    .setWarehousingTransactionFee(optionalProductCategory.get().getWarehousingTransactionFee());
            detailProductCategory.setRecoverableFlag(optionalProductCategory.get().getRecoverableFlag());
            detailProductCategory.setWebDispFlag(optionalProductCategory.get().getWebDispFlag());
            detailProductCategory.setKeywords(optionalProductCategory.get().getKeywords());
            detailProductCategory.setImgFilePath(optionalProductCategory.get().getImgFilePath());
            detailProductCategory.setWebSmallCategoryId(optionalProductCategory.get().getWebSmallCategoryId());
            detailProductCategory.setStandardRankId(optionalProductCategory.get().getProductRanks() != null
                    ? optionalProductCategory.get().getProductRanks().getId()
                    : null);
            detailProductCategory.setMaanagementOut(optionalProductCategory.get().getManagementOut());
        }
        return detailProductCategory;
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @param id
     * @return update delete flag = 1
     * 
     */
    public void deleteProductCategoryById(Integer id) throws Exception {
        logger.info("-- Delete product category by id --");
        Optional<ProductCategory> optionalProductCategory = findById(id);
        if (optionalProductCategory.isEmpty()) {
            logger.error(Constants.RECORD_NOT_FOUND);
            throw new Exception(Constants.RECORD_NOT_FOUND);
        } else {
            // check product category exist table oem_mappings, estimate_details, stocks
            List<OemMappings> oemMappings = oemMappingsRepository.findByProductCategoryIdAndDeleteFlag(id,
                    Constants.DELETE_NONE);
            List<EstimateDetails> estimateDetails = estimateDetailsRepository.findByProductCategoryIdAndDeleteFlag(id,
                    Constants.DELETE_NONE);
            List<Stocks> stocks = stocksRepository.findByProductCategoryIdAndDeleteFlag(id, Constants.DELETE_NONE);
            if (!oemMappings.isEmpty() || !estimateDetails.isEmpty() || !stocks.isEmpty()) {
                logger.error(Constants.RECORD_ALREADY_EXISTS);
                throw new Exception(Constants.RECORD_ALREADY_EXISTS);
            }
            commonDAO.sqlCommonDAO(Constants.PRODUCT_CATEGORIES, Long.valueOf(id), Constants.DELETE,
                    commonService.idUserAccountLogin());
        }
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @param request
     * @return ApiResponse
     * @throws Exception
     * 
     */
    public ApiResponse createProductCategory(EditOrCreateProductCategoryRequest request) throws Exception {
        logger.info("-- Create product category --");
        Integer idLogin = commonService.idUserAccountLogin();
        ProductCategory productCategory = new ProductCategory();
        setProductCategory(productCategory, request);
        productCategory.setCreateAt(new Timestamp(new Date().getTime()));
        productCategory.setUpdatedAt(new Timestamp(new Date().getTime()));
        productCategory.setCreatedBy(idLogin);
        productCategory.setUpdatedBy(idLogin);
        productCategory.setDeleteFlag(0);
        productCategoryRepository.save(productCategory);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @param request
     * @return ApiResponse
     * 
     */
    public ApiResponse editProductCategory(EditOrCreateProductCategoryRequest request) throws Exception {
        logger.info("-- Edit product category by id --");
        Optional<ProductCategory> optionalProductCategory = findById(request.getId());
        if (optionalProductCategory.isEmpty()) {
            logger.error(Constants.RECORD_NOT_FOUND);
            throw new Exception(Constants.RECORD_NOT_FOUND);
        }
        setProductCategory(optionalProductCategory.get(), request);
        productCategoryRepository.save(optionalProductCategory.get());
        commonDAO.sqlCommonDAO(Constants.PRODUCT_CATEGORIES, Long.valueOf(request.getId()), Constants.UPDATE,
                commonService.idUserAccountLogin());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }

    public void setProductCategory(ProductCategory productCategory, EditOrCreateProductCategoryRequest request)
            throws Exception {
        Optional<ProductRanks> optionalProductRank = Optional.empty();
        ;
        Optional<WebSmallCategories> optionalWebSmallCategories = Optional.empty();
        ;
        if (request.getStandardRankId() != null) {
            optionalProductRank = productRanksReposistory.findByIdAndDeleteFlag(request.getStandardRankId(),
                    Constants.DELETE_NONE);
        }
        if (request.getWebSmallCategoryId() != null) {
            optionalWebSmallCategories = webSmallCategoriesRepository.findById(request.getWebSmallCategoryId());
        }

        if ((request.getStandardRankId() != null && optionalProductRank.isEmpty())
                || (request.getWebSmallCategoryId() != null && optionalWebSmallCategories.isEmpty())) {
            logger.error(Constants.RECORD_NOT_FOUND);
            throw new Exception(Constants.RECORD_NOT_FOUND);
        }
        productCategory.setCategoryName(request.getCategoryName().trim());
        productCategory.setPickupFeeNoTax(request.getPickupFeeNoTax());
        productCategory.setWarehousingFeeNoTax(request.getWarewhousingFeeNoTax());
        productCategory.setWarehousingTransactionFee(request.getWarehousingTransactionFee());
        productCategory.setProductRanks(!optionalProductRank.isEmpty() ? optionalProductRank.get() : null);
        productCategory.setRecoverableFlag(request.getRecoverableFlag());
        productCategory.setKeywords(request.getKeywords().trim());
        productCategory.setWebDispFlag(request.getWebDispFlag());
        productCategory.setWebSmallCategoryId(request.getWebSmallCategoryId());
        productCategory.setManagementOut(request.getManagementOut());
        productCategory.setImgFilePath(request.getImgFilePath() != null ? request.getImgFilePath().trim() : null);
    }
}
