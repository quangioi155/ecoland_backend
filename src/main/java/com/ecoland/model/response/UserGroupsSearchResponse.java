package com.ecoland.model.response;

import lombok.Data;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Data
public class UserGroupsSearchResponse {

    private Integer id;
    private String groupName;
    private String contactCustomerFlag;
    private String driverFlag;
    private String vehicleDispatchFlag;
    private String zecFlag;
    private String manageFlag;
    private String warehouseFlag;
    private String systemFlag;

}
