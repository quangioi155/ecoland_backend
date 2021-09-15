package com.ecoland.repository.dao;

import com.ecoland.common.CommonDao;
import com.ecoland.common.Constants;
import com.ecoland.model.ResultPageResponse;
import com.ecoland.model.request.system.SearchOutsourceCompanyRequest;
import com.ecoland.model.response.system.SearchOutsourceCompanyResponse;
import com.ecoland.utils.PaginationResult;
import com.ecoland.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class OutsourceCompanyDAO extends CommonDao {

    private static final Logger logger = LoggerFactory.getLogger(OutsourceCompanyDAO.class);
    @Autowired
    private EntityManager entityManager;

    public ResultPageResponse searchOutsourceCompany(SearchOutsourceCompanyRequest request) {

        ResultPageResponse resultPageResponse = new ResultPageResponse();
        Session session = entityManager.unwrap(Session.class);
        StringBuilder sql = new StringBuilder();

        sql.append("Select osc.id,\r\n" + "osc.outsource_company_name as name,\r\n"
                + "osc.address1 || ' ' || osc.address2 || ' ' || osc.address3 as address,\r\n"
                + "osc.tel tel,\r\n" + "prt.partner_name as partnerName\r\n"
                + "From outsource_companies osc\r\n"
                + "Left Join partners prt ON osc.partner_id= prt.id\r\n" + "Where osc." + Constants.DELETE_FLAG + "");

        if (request.getPartnerId() != null) {
            sql.append(" AND osc.partner_id =:partnerId");
        }
        if (StringUtils.isNotBlank(request.getName() != null ? request.getName() : "")) {
            sql.append(" AND  osc.outsource_company_name LIKE :outsourceCompanyName ");
        }

        sql.append(" ORDER BY osc.id ASC");

        @SuppressWarnings("unchecked")
        NativeQuery<SearchOutsourceCompanyResponse> query = session.createNativeQuery(sql.toString());
        query.setCacheable(true);

        if (request.getPartnerId() != null) {
            query.setParameter("partnerId", request.getPartnerId());
        }
        if (StringUtils.isNotBlank(request.getName() != null ? request.getName() : "")) {
            query.setParameter("outsourceCompanyName",
                    Constants.SEARCH_LIKE + request.getName().trim() + Constants.SEARCH_LIKE);
        }

        session.close();
        Utils.addScalr(query, SearchOutsourceCompanyResponse.class);
        PaginationResult<SearchOutsourceCompanyResponse> result = new PaginationResult<SearchOutsourceCompanyResponse>(query,
                request.getPageNo(), request.getPageSize(), Constants.MAX_NAVIGATION_RESULT);
        resultPageResponse.setItems(result.getList());
        resultPageResponse.setTotalPages(result.getTotalPages());
        resultPageResponse.setTotalItems(result.getTotalRecords());
        resultPageResponse.setCurrentPage(result.getCurrentPage());
        logger.info("-- Select outsource company with request success -- ");
        return resultPageResponse;
    }
}
