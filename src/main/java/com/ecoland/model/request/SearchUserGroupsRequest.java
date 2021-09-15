package com.ecoland.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchUserGroupsRequest extends PaginationRequest {
    private String groupName;
}
