package com.ecoland.repository.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecoland.common.CommonFilter;
import com.ecoland.common.Constants;
import com.ecoland.model.ResultPageResponse;
import com.ecoland.model.request.RequestDTO;
import com.ecoland.model.response.system.SearchProductCategoryResponse;
import com.ecoland.utils.PaginationResult;
import com.ecoland.utils.Utils;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Repository
public class ProductCategoryDAO {
    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryDAO.class);

    @Autowired
    private EntityManager entityManager;

    public ResultPageResponse searchProductCategory(RequestDTO requestDTO) {
        logger.info("-- Get list search product category -- ");
        Session session = entityManager.unwrap(Session.class);
        ResultPageResponse resultPageResponse = new ResultPageResponse();
        StringBuilder sql = new StringBuilder();
        sql.append("Select pdc.id as id," + "pdc.category_name categoryName,"
                + "pdc.warehousing_transaction_fee warehousingTransactionFee,"
                + "pdr.product_rank_name productRankName," + "pdc.recoverable_flag recoverabeFlag,"
                + "pdc.web_disp_flag webDispFlag," + "pdc.keywords as keywords\r\n" + "From product_categories pdc\r\n"
                + "Left Join product_ranks pdr on pdc.standard_rank_id=pdr.id\r\n" + "Where pdc."
                + Constants.DELETE_FLAG);
        List<RequestDTO.Filter> filters = requestDTO.getFilters().stream().filter(Objects::nonNull)
                .collect(Collectors.toList());
        Map<String, String> filterMap = filters.stream().collect(HashMap::new,
                (k, v) -> k.put(v.getKey(), v.getValues()), HashMap::putAll);
        if (filterMap.containsKey(CommonFilter.PRODUCT_CATEGORY.CATETGORY_NAME.toString())) {
            sql.append(" AND pdc.category_name LIKE :categoryName");
        }
        if (filterMap.containsKey(CommonFilter.PRODUCT_CATEGORY.PRODUCT_RANK_ID.toString())) {
            sql.append(" AND pdc.standard_rank_id =:productRankId");
        }
        if (filterMap.containsKey(CommonFilter.PRODUCT_CATEGORY.RECOVERABLE_FLAG.toString())) {
            sql.append(" AND pdc.recoverable_flag =:recoverableFlag");
        }
        if (filterMap.containsKey(CommonFilter.PRODUCT_CATEGORY.WEB_DISP_FLAG.toString())) {
            sql.append(" AND pdc.web_disp_flag =:webDispFlag ");
        }
        sql.append(" Order by pdc.id asc");
        @SuppressWarnings("unchecked")
        NativeQuery<SearchProductCategoryResponse> query = session.createNativeQuery(sql.toString());
        query.setCacheable(true);
        String categoryName = filterMap.get(CommonFilter.PRODUCT_CATEGORY.CATETGORY_NAME.toString());
        String productRankId = filterMap.get(CommonFilter.PRODUCT_CATEGORY.PRODUCT_RANK_ID.toString());
        String recoverableFlag = filterMap.get(CommonFilter.PRODUCT_CATEGORY.RECOVERABLE_FLAG.toString());
        String webDispFlag = filterMap.get(CommonFilter.PRODUCT_CATEGORY.WEB_DISP_FLAG.toString());
        if (StringUtils.isNotBlank(categoryName)) {
            query.setParameter("categoryName", Constants.SEARCH_LIKE + categoryName + Constants.SEARCH_LIKE);
        }
        if (StringUtils.isNumeric(productRankId)) {
            query.setParameter("productRankId", Integer.parseInt(productRankId));
        }

        if (StringUtils.isNotBlank(recoverableFlag)) {
            query.setParameter("recoverableFlag", Boolean.parseBoolean(recoverableFlag));
        }
        if (StringUtils.isNotBlank(webDispFlag)) {
            query.setParameter("webDispFlag", Boolean.parseBoolean(webDispFlag));
        }
        session.close();
        Utils.addScalr(query, SearchProductCategoryResponse.class);
        PaginationResult<SearchProductCategoryResponse> result = new PaginationResult<SearchProductCategoryResponse>(
                query, requestDTO.getPageNo(), requestDTO.getPageSize(), Constants.MAX_NAVIGATION_RESULT);
        resultPageResponse.setItems(result.getList());
        resultPageResponse.setTotalPages(result.getTotalPages());
        resultPageResponse.setTotalItems(result.getTotalRecords());
        resultPageResponse.setCurrentPage(result.getCurrentPage());
        return resultPageResponse;
    }
}
