package com.ecoland.repository.dao;

import com.ecoland.common.Constants;
import com.ecoland.entity.WebSmallCategories;
import com.ecoland.model.ResultPageResponse;
import com.ecoland.model.request.WebSmallCategoryRequest;
import com.ecoland.model.response.IdValueResponse;
import com.ecoland.model.response.WebSmallCategoryResponse;
import com.ecoland.utils.PaginationResult;
import com.ecoland.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class SmallCategoryDao {

    @Autowired
    private EntityManager entityManager;

    public ResultPageResponse getSmallCategory(WebSmallCategoryRequest request){

        Session session = entityManager.unwrap(Session.class);
        StringBuilder sql = new StringBuilder();
        sql.append(
                "Select wsc.id as id,wsc.category_name as categoryName,wlc.category_name as categoryLargeName "
                        + "From web_small_categories wsc \n" + "left Join web_large_categories wlc on wlc.id=wsc.large_category_id \n"
                        + "Where 1=1 and wsc."+ Constants.DELETE_FLAG+" ");
        if (StringUtils.isNotBlank(request.getCategoryName())) {
            sql.append(" AND wsc.category_name LIKE :categoryName ");
        }
        if (request.getId() != null) {
            sql.append(" AND wlc.id =:id ");
        }
        sql.append(" Order by id asc");

        @SuppressWarnings("unchecked")
        NativeQuery<WebSmallCategoryResponse> query = session.createNativeQuery(sql.toString());
        if (StringUtils.isNotBlank(request.getCategoryName())) {
            query.setParameter("categoryName", "%" +request.getCategoryName()+"%");
        }
        if (request.getId() != null) {
            query.setParameter("id", request.getId());
        }

        Utils.addScalr(query, WebSmallCategoryResponse.class);
        PaginationResult<WebSmallCategoryResponse> result = new PaginationResult<WebSmallCategoryResponse>(query,
                request.getPageNo(), request.getPageSize(), Constants.MAX_NAVIGATION_RESULT);
        ResultPageResponse resultPageResponse = new ResultPageResponse();
        resultPageResponse.setItems(result.getList());
        resultPageResponse.setTotalPages(result.getTotalPages());
        resultPageResponse.setTotalItems(result.getTotalRecords());
        resultPageResponse.setCurrentPage(result.getCurrentPage());
        // 1 2 3 4 5 ... 11 12 13
        return resultPageResponse;
    }

    public List<IdValueResponse> getLargeCategory(){

        Session session = entityManager.unwrap(Session.class);
        StringBuilder sql = new StringBuilder();
        sql.append(
                "Select wlc.id as value,wlc.category_name as name "
                        + "From web_large_categories wlc \n"
                        + "Where 1=1 and wlc."+ Constants.DELETE_FLAG+"");

        sql.append(" Order by value asc");

        @SuppressWarnings("unchecked")
        NativeQuery<IdValueResponse> query = session.createNativeQuery(sql.toString());


        Utils.addScalr(query, IdValueResponse.class);
        List<IdValueResponse> listResponse = query.getResultList();
        return listResponse;
    }

    public void delete(WebSmallCategories webSmallCategories){
        entityManager.merge(webSmallCategories);
    }


}
