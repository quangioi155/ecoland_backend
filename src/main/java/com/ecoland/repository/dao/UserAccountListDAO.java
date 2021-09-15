package com.ecoland.repository.dao;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecoland.common.Constants;
import com.ecoland.model.ResultPageResponse;
import com.ecoland.model.request.UserAccountListRequest;
import com.ecoland.model.response.UserAccountListReponse;
import com.ecoland.utils.PaginationResult;
import com.ecoland.utils.Utils;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Repository
public class UserAccountListDAO {
    @Autowired
    private EntityManager entityManager;

    public ResultPageResponse listUserAccount(UserAccountListRequest request) {

        Session session = entityManager.unwrap(Session.class);
        StringBuilder sql = new StringBuilder();
        sql.append(
                "Select ua.id as id,prt.partner_name as partnerName,brt.branch_short_name as branchShortName,ua.login_id as loginId,"
                        + "ua.account_name as accountName,usg.group_name as groupName,ua.description as description\n"
                        + "From user_accounts ua \n" + "Left Join partners prt on ua.partner_id=prt.id \n"
                        + "Left Join branches brt on ua.branch_id=brt.id \n"
                        + "Left Join user_groups usg on ua.user_group_id=usg.id \n" + "Where ua."
                        + Constants.DELETE_FLAG + "");
        if (request.getPartnerId() != null) {
            sql.append(" AND ua.partner_id =:partnerId ");
        }
        if (request.getBranchId() != null) {
            sql.append(" AND ua.branch_id =:branchId ");
        }
        if (StringUtils.isNotBlank(request.getLoginId() != null ? request.getLoginId() : "")) {
            sql.append(" AND ua.login_id LIKE :loginId");
        }
        if (StringUtils.isNotBlank(request.getAccountName() != null ? request.getAccountName() : "")) {
            sql.append(" AND ua.account_name LIKE :accountName");
        }
        sql.append(" Order by id asc");

        @SuppressWarnings("unchecked")
        NativeQuery<UserAccountListReponse> query = session.createNativeQuery(sql.toString());
        if (request.getPartnerId() != null) {
            query.setParameter("partnerId", request.getPartnerId());
        }
        if (request.getBranchId() != null) {
            query.setParameter("branchId", request.getBranchId());
        }
        if (StringUtils.isNotBlank(request.getLoginId() != null ? request.getLoginId() : "")) {
            query.setParameter("loginId", "%" + request.getLoginId().trim() + "%");
        }
        if (StringUtils.isNotBlank(request.getAccountName() != null ? request.getAccountName() : "")) {
            query.setParameter("accountName", "%" + request.getAccountName().trim() + "%");
        }
        Utils.addScalr(query, UserAccountListReponse.class);
        PaginationResult<UserAccountListReponse> result = new PaginationResult<UserAccountListReponse>(query,
                request.getPageNo(), request.getPageSize(), Constants.MAX_NAVIGATION_RESULT);
        ResultPageResponse resultPageResponse = new ResultPageResponse();
        resultPageResponse.setItems(result.getList());
        resultPageResponse.setTotalPages(result.getTotalPages());
        resultPageResponse.setTotalItems(result.getTotalRecords());
        resultPageResponse.setCurrentPage(result.getCurrentPage());
        // 1 2 3 4 5 ... 11 12 13
        return resultPageResponse;
    }
}
