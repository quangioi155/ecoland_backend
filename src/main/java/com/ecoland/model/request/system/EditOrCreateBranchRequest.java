package com.ecoland.model.request.system;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ecoland.common.Constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditOrCreateBranchRequest {
    private Integer id;

    private Integer partnerId;

    @NotBlank(message = Constants.VALIDATE_THE_FIELD)
    @Size(max = 30, message = Constants.VALIDATE_MAX_SIZE_30)
    private String branchName;

    @NotBlank(message = Constants.VALIDATE_THE_FIELD)
    @Size(max = 20, message = Constants.VALIDATE_MAX_SIZE_20)
    private String branchShortName;

    @Size(max = 7, message = Constants.VALIDATE_MAX_SIZE_7)
    private String postalCode;

    @Size(max = 60, message = Constants.VALIDATE_MAX_SIZE_60)
    private String address1;

    @Size(max = 60, message = Constants.VALIDATE_MAX_SIZE_60)
    private String address2;

    @Size(max = 60, message = Constants.VALIDATE_MAX_SIZE_60)
    private String address3;

    private Integer inputCorpSite;

    private Integer delivCorpSite;

    @Size(max = 13, message = Constants.VALIDATE_MAX_SIZE_13)
    private String tel;

    @Size(max = 13, message = Constants.VALIDATE_MAX_SIZE_13)
    private String fax;

    private Date startDate;

    private Date endDate;
}
