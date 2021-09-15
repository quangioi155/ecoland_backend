package com.ecoland.repository.dao;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecoland.common.Constants;
import com.ecoland.model.ResultPageResponse;
import com.ecoland.model.request.SearchUserGroupsRequest;
import com.ecoland.model.response.UserGroupsSearchResponse;
import com.ecoland.utils.PaginationResult;
import com.ecoland.utils.Utils;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Repository
public class UserGroupsDAO {
    @Autowired
    private EntityManager entityManager;

    /**
     * 
     * @author thaotv@its-global.vn
     * @param request
     * @return ResultPageResponse
     */
    public ResultPageResponse searchUserGroup(SearchUserGroupsRequest request) {
        Session session = entityManager.unwrap(Session.class);
        ResultPageResponse resultPageResponse = new ResultPageResponse();
        StringBuilder sql = new StringBuilder();
        sql.append("Select id,group_name as groupName,\n"
                + "CASE contact_customer_flag WHEN TRUE THEN '●' ELSE '' END contactCustomerFlag,\n"
                + "CASE driver_flag WHEN TRUE THEN '●' ELSE '' END driverFlag,\n"
                + "CASE vehicle_dispatch_flag WHEN TRUE THEN '●' ELSE '' END vehicleDispatchFlag,\n"
                + "CASE zec_flag WHEN TRUE THEN '●' ELSE '' END zecFlag,\n"
                + "CASE manage_flag WHEN TRUE THEN '●' ELSE '' END manageFlag,\n"
                + "CASE warehouse_flag WHEN TRUE THEN '●' ELSE '' END warehouseFlag,\n"
                + "CASE system_flag WHEN TRUE THEN '●' ELSE '' END systemFlag\n" + " From user_groups\n" + "Where "
                + Constants.DELETE_FLAG);
        if (StringUtils.isNotBlank(request.getGroupName() != null ? request.getGroupName().trim() : "")) {
            sql.append(" AND group_name LIKE :groupName");
        }
        sql.append(" Order by id asc");
        @SuppressWarnings("unchecked")
        NativeQuery<UserGroupsSearchResponse> query = session.createNativeQuery(sql.toString());
        query.setCacheable(true);
        if (StringUtils.isNotBlank(request.getGroupName() != null ? request.getGroupName() : "")) {
            query.setParameter("groupName",
                    Constants.SEARCH_LIKE + request.getGroupName().trim() + Constants.SEARCH_LIKE);
        }
        session.close();
        Utils.addScalr(query, UserGroupsSearchResponse.class);
        PaginationResult<UserGroupsSearchResponse> result = new PaginationResult<UserGroupsSearchResponse>(query,
                request.getPageNo(), request.getPageSize(), Constants.MAX_NAVIGATION_RESULT);
        resultPageResponse.setItems(result.getList());
        resultPageResponse.setTotalPages(result.getTotalPages());
        resultPageResponse.setTotalItems(result.getTotalRecords());
        resultPageResponse.setCurrentPage(result.getCurrentPage());
        return resultPageResponse;
    }

}
