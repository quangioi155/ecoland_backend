package com.ecoland.repository.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecoland.model.request.system.SearchRankRequest;
import com.ecoland.model.response.system.SearchRankResponse;

/**
 * @class CompanyGroupDao
 * 
 * @summary Dao of product rank function
 * 
 * @author ITSG - HoanNNC
 */
@Repository
public class ProductRankDao {
    @Autowired
    private EntityManager entityManager;
    
    @SuppressWarnings({ "unchecked"})
    public List<SearchRankResponse> getRankList(SearchRankRequest req) {
        Session session = entityManager.unwrap(Session.class);

        String sql = "SELECT pr.id, pr.product_rank_name as productRankName, pr.product_size as productSize, " 
        + "pr.weight as weight, pr.price_notax as priceNotax"
        + "\nFrom product_ranks pr \n WHERE pr.delete_flag = 0";

        if (StringUtils.isNotEmpty(req.getRankName())) {
            sql += " AND pr.product_rank_name LIKE :rankName";
        }
        sql += " \nORDER BY pr.id ASC";

        Query<SearchRankResponse> query = session.createNativeQuery(sql, "SearchRankDtoMap");
        if (StringUtils.isNotEmpty(req.getRankName())) {
            query.setParameter("rankName", "%" + req.getRankName().trim() + "%");
        }
        int offset = (req.getPageNo() - 1) * req.getPageSize();
        query.setFirstResult(offset);
        query.setMaxResults(req.getPageSize());

        return query.getResultList();
    }
    
    public int getTotalRecord(SearchRankRequest req) {
        Session session = entityManager.unwrap(Session.class);

        String sql = "SELECT COUNT(*)" 
        + "\nFrom product_ranks pr \n WHERE pr.delete_flag = 0";

        if (StringUtils.isNotEmpty(req.getRankName())) {
            sql += " AND pr.product_rank_name LIKE :rankName";
        }
        Query<?> query = session.createNativeQuery(sql);
        if (StringUtils.isNotEmpty(req.getRankName())) {
            query.setParameter("rankName", "%" + req.getRankName().trim() + "%");
        }
        
        return Integer.parseInt(query.getSingleResult().toString());
    }
}
