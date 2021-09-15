package com.ecoland.repository.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecoland.model.request.system.SearchIntroductionRequest;
import com.ecoland.model.response.system.SearchIntroductionResponse;

/**
 * @class IntroductionDAO
 * 
 * @summary Dao of instroduction function
 * 
 * @author ITSG - HoanNNC
 */
@Repository
public class IntroductionDAO {
    @Autowired
    private EntityManager entityManager;
    
    @SuppressWarnings({ "unchecked"})
    public List<SearchIntroductionResponse> getIntroductionList(SearchIntroductionRequest req) {
        Session session = entityManager.unwrap(Session.class);

        String sql = "SELECT ins.id, ins.introduction_name as introductionName, ins.pay_timing as payTiming, ins.pay_unit as payUnit, " 
        + "CASE WHEN ins.pay_unit=1 THEN ins.pay_amount || '円' ELSE ins.pay_percent || '%' END as payAmount, ins.discount_unit as discountUnit, "
        + "CASE WHEN ins.discount_unit=1 THEN ins.discount || '円' ELSE ins.discount_percent || '%' END as discount, ins.sort_no as sortNo"
        + "\nFrom introductions ins \n WHERE ins.delete_flag = 0";

        if (StringUtils.isNotEmpty(req.getIntroductionName())) {
            sql += " AND ins.introduction_name LIKE :insName";
        }
        if(req.getPayTiming() != null) {
            sql += " AND ins.pay_timing = :payTiming";
        }
        if(req.getPayUnit() != null) {
            sql += " AND ins.pay_unit = :payUnit";
        }
        if(req.getDiscountUnit() != null) {
            sql += " AND ins.discount_unit = :discountUnit";
        }
        sql += " \nORDER BY ins.id ASC";

        Query<SearchIntroductionResponse> query = session.createNativeQuery(sql, "SearchIntroductionDtoMap");
        if (StringUtils.isNotEmpty(req.getIntroductionName())) {
            query.setParameter("insName", "%" + req.getIntroductionName().trim() + "%");
        }
        if(req.getPayTiming() != null) {
            query.setParameter("payTiming", req.getPayTiming());
        }
        if(req.getPayUnit() != null) {
            query.setParameter("payUnit", req.getPayUnit());
        }
        if(req.getDiscountUnit() != null) {
            query.setParameter("discountUnit", req.getDiscountUnit());
        }
        int offset = (req.getPageNo() - 1) * req.getPageSize();
        query.setFirstResult(offset);
        query.setMaxResults(req.getPageSize());

        return query.getResultList();
    }
    
    public int getTotalRecord(SearchIntroductionRequest req) {
        Session session = entityManager.unwrap(Session.class);
        String sql = "SELECT COUNT(*)" 
        + "\nFrom introductions ins \n WHERE ins.delete_flag = 0";

        if (StringUtils.isNotEmpty(req.getIntroductionName())) {
            sql += " AND ins.introduction_name LIKE :insName";
        }
        if(req.getPayTiming() != null) {
            sql += " AND ins.pay_timing = :payTiming";
        }
        if(req.getPayUnit() != null) {
            sql += " AND ins.pay_unit = :payUnit";
        }
        if(req.getDiscountUnit() != null) {
            sql += " AND ins.discount_unit = :discountUnit";
        }
        Query<?> query = session.createNativeQuery(sql);
        
        if (StringUtils.isNotEmpty(req.getIntroductionName())) {
            query.setParameter("insName", "%" + req.getIntroductionName().trim() + "%");
        }
        if(req.getPayTiming() != null) {
            query.setParameter("payTiming", req.getPayTiming());
        }
        if(req.getPayUnit() != null) {
            query.setParameter("payUnit", req.getPayUnit());
        }
        if(req.getDiscountUnit() != null) {
            query.setParameter("discountUnit", req.getDiscountUnit());
        }
        
        return Integer.parseInt(query.getSingleResult().toString());
    }
    
}
