package com.ecoland.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ecoland.common.Constants;

import lombok.Data;

@Data
public class EditOrCreateUserGroupRequest {
    private Integer id;
    
    @NotBlank(message = Constants.VALIDATE_THE_FIELD)
    @Size(max = 30, message = Constants.VALIDATE_MAX_SIZE_30)
    private String groupName;
    private Boolean contactCustomerFlag;
    private Boolean driverFlag;
    private Boolean vehicleDispatchFlag;
    private Boolean zecFlag;
    private Boolean manageFlag;
    private Boolean warehouseFlag;
    private Boolean systemFlag;
}
