package com.ecoland.repository.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecoland.common.CommonDao;
import com.ecoland.model.request.system.SearchCompanyRequest;
import com.ecoland.model.response.system.SearchCompanyResponse;

/**
 * @class CompanyGroupDao
 * 
 * @summary Dao of company group function
 * 
 * @author ITSG - HoanNNC
 */
@Repository
public class CompanyGroupDao extends CommonDao {

    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings({ "unchecked" })
    public List<SearchCompanyResponse> getCompanyGroupList(SearchCompanyRequest request) {
        Session session = entityManager.unwrap(Session.class);

        String hql = "SELECT prt.id, prt.partner_name as partnerName, prt.postal_code as postalCode, "
                + "COALESCE(prt.address1, '') || COALESCE(prt.address2, '') || COALESCE(prt.address3, '') as address, prt.tel, prt.fax"
                + "\nFrom Partners prt \n WHERE prt.delete_flag = 0";

        if (StringUtils.isNotEmpty(request.getPartnerName())) {
            hql += " AND prt.partner_name LIKE :partnerName";
        }
        if (request.isMainFlag()) {
            hql += " AND prt.main_flag = TRUE";
        }
        hql += " \nORDER BY prt.id ASC";

        Query<SearchCompanyResponse> query = session.createNativeQuery(hql, "SearchPartnerDtoMap");
        if (StringUtils.isNotEmpty(request.getPartnerName())) {
            query.setParameter("partnerName", "%" + request.getPartnerName().trim() + "%");
        }
        int offset = (request.getPageNo() - 1) * request.getPageSize();
        query.setFirstResult(offset);
        query.setMaxResults(request.getPageSize());

        return query.getResultList();
    }

    public int getToTalRecord(SearchCompanyRequest request) {

        Session session = entityManager.unwrap(Session.class);

        String hql = "SELECT COUNT(*)" + "\nFrom Partners prt \n WHERE prt.delete_flag = 0";

        if (StringUtils.isNotEmpty(request.getPartnerName())) {
            hql += " AND prt.partner_name LIKE :partnerName";
        }
        if (request.isMainFlag()) {
            hql += " AND prt.main_flag = TRUE";
        }
        Query<?> query = session.createNativeQuery(hql);
        if (StringUtils.isNotEmpty(request.getPartnerName())) {
            query.setParameter("partnerName", "%" + request.getPartnerName().trim() + "%");
        }

        return Integer.parseInt(query.getSingleResult().toString());
    }

}
