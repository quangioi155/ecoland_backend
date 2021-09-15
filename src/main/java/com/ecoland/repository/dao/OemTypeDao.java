package com.ecoland.repository.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecoland.model.request.system.SearchOemTypeRequest;
import com.ecoland.model.response.system.SearchOemTypeResponse;

/**
 * @class OemTypeDao
 * 
 * @summary Dao of oem type function
 * 
 * @author ITSG - HoanNNC
 */
@Repository
public class OemTypeDao {

    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<SearchOemTypeResponse> getOemTypeList(SearchOemTypeRequest req) {
        Session session = entityManager.unwrap(Session.class);

        String sql = "SELECT ot.id, ot.oem_name as oemName, ot.sort_no as sortNo "
                + "\nFrom oem_types ot \n WHERE ot.delete_flag = 0";

        if (StringUtils.isNotEmpty(req.getOemName())) {
            sql += " AND ot.oem_name LIKE :oemName";
        }
        sql += " \nORDER BY ot.id ASC";

        Query<SearchOemTypeResponse> query = session.createNativeQuery(sql, "SearchOemTypeDtoMap");
        if (StringUtils.isNotEmpty(req.getOemName())) {
            query.setParameter("oemName", "%" + req.getOemName().trim() + "%");
        }
        int offset = (req.getPageNo() - 1) * req.getPageSize();
        query.setFirstResult(offset);
        query.setMaxResults(req.getPageSize());

        return query.getResultList();
    }

    public int getTotalRecord(SearchOemTypeRequest req) {
        Session session = entityManager.unwrap(Session.class);

        String sql = "SELECT COUNT(*)" + "\nFrom oem_types ot \n WHERE ot.delete_flag = 0";

        if (StringUtils.isNotEmpty(req.getOemName())) {
            sql += " AND ot.oem_name LIKE :oemName";
        }
        Query<?> query = session.createNativeQuery(sql);
        if (StringUtils.isNotEmpty(req.getOemName())) {
            query.setParameter("oemName", "%" + req.getOemName().trim() + "%");
        }

        return Integer.parseInt(query.getSingleResult().toString());
    }
}
