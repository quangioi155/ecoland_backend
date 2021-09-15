package com.ecoland.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserAccountListRequest extends PaginationRequest {
    private Integer partnerId;
    private Integer branchId;
    private String loginId;
    private String accountName;
}
