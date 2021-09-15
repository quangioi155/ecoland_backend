package com.ecoland.model.response;

import lombok.Data;

@Data
public class DetailUserGroupsResponse {
    private Integer id;
    private String groupName;
    private Boolean contactCustomerFlag;
    private Boolean driverFlag;
    private Boolean vehicleDispatchFlag;
    private Boolean zecFlag;
    private Boolean manageFlag;
    private Boolean warehouseFlag;
    private Boolean systemFlag;
}