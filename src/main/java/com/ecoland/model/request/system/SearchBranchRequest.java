package com.ecoland.model.request.system;

import com.ecoland.model.request.PaginationRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchBranchRequest extends PaginationRequest{

    private String branchName;
    private Integer partnerId;
}
