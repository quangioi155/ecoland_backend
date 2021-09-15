package com.ecoland.model.request.system;

import com.ecoland.model.request.PaginationRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SearchRankRequest extends PaginationRequest {
    String rankName;
}
