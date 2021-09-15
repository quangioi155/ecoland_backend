package com.ecoland.model.request;

import lombok.Data;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Data
public class PaginationRequest {

    private Integer pageSize;
    private Integer pageNo;
}
