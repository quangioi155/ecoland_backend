package com.ecoland.repository.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecoland.common.CommonDao;
import com.ecoland.common.Constants;
import com.ecoland.model.response.system.SearchWebLargeCategoryResponse;
import com.ecoland.model.request.system.SearchWebLargeCategoryRequest;

/**
 * 
 * @author Tien-ITS
 * 
 */
@Repository
public class WebLargeCategoriesDAO extends CommonDao {
    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings({ "unchecked" })
    public List<SearchWebLargeCategoryResponse> getWebLargeCategoryList(SearchWebLargeCategoryRequest request) {
        Session session = entityManager.unwrap(Session.class);

        String hql = "SELECT w.id, w.category_name as categoryName " + "\nFrom web_large_categories w \n WHERE w."
                + Constants.DELETE_FLAG;

        if (StringUtils.isNotEmpty(request.getCategoryName())) {
            hql += " AND w.category_name LIKE :categoryName";
        }
        hql += " \nORDER BY w.id ASC";

        Query<SearchWebLargeCategoryResponse> query = session.createNativeQuery(hql, "SearchWebLargeCategoryDtoMap");
        if (StringUtils.isNotEmpty(request.getCategoryName())) {
            query.setParameter("categoryName", "%" + request.getCategoryName().trim() + "%");
        }
        int offset = (request.getPageNo() - 1) * request.getPageSize();
        query.setFirstResult(offset);
        query.setMaxResults(request.getPageSize());

        return query.getResultList();
    }

    public int getToTalRecord(SearchWebLargeCategoryRequest request) {
        Session session = entityManager.unwrap(Session.class);

        String hql = "SELECT COUNT(*)" + "\nFrom web_large_categories w \n WHERE w." + Constants.DELETE_FLAG;

        if (StringUtils.isNotEmpty(request.getCategoryName())) {
            hql += " AND w.category_name LIKE :categoryName";
        }
        Query query = session.createNativeQuery(hql);
        if (StringUtils.isNotEmpty(request.getCategoryName())) {
            query.setParameter("categoryName", "%" + request.getCategoryName().trim() + "%");
        }

        return Integer.parseInt(query.getSingleResult().toString());
    }
}
