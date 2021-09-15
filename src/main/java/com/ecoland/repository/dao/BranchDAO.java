package com.ecoland.repository.dao;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecoland.common.Constants;
import com.ecoland.model.ResultPageResponse;
import com.ecoland.model.request.system.SearchBranchRequest;
import com.ecoland.model.response.system.SearchBranchResponse;
import com.ecoland.utils.PaginationResult;
import com.ecoland.utils.Utils;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Repository
public class BranchDAO {

    private static final Logger logger = LoggerFactory.getLogger(BranchDAO.class);
    @Autowired
    private EntityManager entityManager;

    public ResultPageResponse searchBranch(SearchBranchRequest request) {
        logger.info("-- Select branch with request -- ");
        ResultPageResponse resultPageResponse = new ResultPageResponse();
        Session session = entityManager.unwrap(Session.class);
        StringBuilder sql = new StringBuilder();
        sql.append("Select brt.id,\r\n" + "prt.partner_name partnerName,\r\n" + "brt.branch_name branchName,\r\n"
                + "brt.branch_short_name branchShortName,\r\n" + "brt.postal_code postalCode,\r\n"
                + "brt.address1 || ' ' || brt.address2 || ' ' || brt.address3 as address,\r\n"
                + "brt.input_corp_site inputCorpSite,\r\n" + "brt.deliv_corp_site delivCorpSite,\r\n" + "brt.tel,\r\n"
                + "brt.fax,\r\n" + "brt.start_date startDate,\r\n" + "brt.end_date endDate\r\n"
                + "From branches brt\r\n" + "LEFT JOIN partners prt on brt.partner_id=prt.id\r\n" + "Where brt."
                + Constants.DELETE_FLAG + "");
        if (StringUtils.isNotBlank(request.getBranchName() != null ? request.getBranchName() : "")) {
            sql.append(" AND brt.branch_name LIKE :branchName ");
        }
        if (request.getPartnerId() != null) {
            sql.append(" AND brt.partner_id =:partnerId ");
        }
        sql.append(" Order by brt.id asc");
        @SuppressWarnings("unchecked")
        NativeQuery<SearchBranchResponse> query = session.createNativeQuery(sql.toString());
        query.setCacheable(true);
        if (StringUtils.isNotBlank(request.getBranchName() != null ? request.getBranchName() : "")) {
            query.setParameter("branchName",
                    Constants.SEARCH_LIKE + request.getBranchName().trim() + Constants.SEARCH_LIKE);
        }
        if (request.getPartnerId() != null) {
            query.setParameter("partnerId", request.getPartnerId());
        }
        session.close();
        Utils.addScalr(query, SearchBranchResponse.class);
        PaginationResult<SearchBranchResponse> result = new PaginationResult<SearchBranchResponse>(query,
                request.getPageNo(), request.getPageSize(), Constants.MAX_NAVIGATION_RESULT);
        resultPageResponse.setItems(result.getList());
        resultPageResponse.setTotalPages(result.getTotalPages());
        resultPageResponse.setTotalItems(result.getTotalRecords());
        resultPageResponse.setCurrentPage(result.getCurrentPage());
        logger.info("-- Select branch with request success -- ");
        return resultPageResponse;
    }
}
