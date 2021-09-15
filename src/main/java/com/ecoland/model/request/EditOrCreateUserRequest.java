package com.ecoland.model.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ecoland.common.Constants;

import lombok.Data;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Data
public class EditOrCreateUserRequest {

    private Long id;

    @NotBlank(message = Constants.VALIDATE_THE_FIELD)
    @Size(max = 20, message = Constants.VALIDATE_MAX_SIZE_20)
    private String loginId;

    @NotBlank(message = Constants.VALIDATE_THE_FIELD)
    @Size(max = 20, message = Constants.VALIDATE_MAX_SIZE_20)
    private String loginPassword;

    @Size(max = 20, message = Constants.VALIDATE_MAX_SIZE_20)
    private String employeeCd;

    @NotBlank(message = Constants.VALIDATE_THE_FIELD)
    @Size(max = 30, message = Constants.VALIDATE_MAX_SIZE_30)
    private String accountName;

    @Size(max = 30, message = Constants.VALIDATE_MAX_SIZE_30)
    private String accountNameKana;

    @NotNull(message = Constants.VALIDATE_THE_FIELD)
    private Integer userGroupId;

    @NotNull(message = Constants.VALIDATE_THE_FIELD)
    private Integer partnerId;

    @NotNull(message = Constants.VALIDATE_THE_FIELD)
    private Integer branchId;

    private String description;
}
