package com.ecoland.model.request.system;

import com.ecoland.model.request.PaginationRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 
 * @author quannn@its-global.vn
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchOutsourceCompanyRequest extends PaginationRequest {

    private Integer id;
    private String name;
    private Integer partnerId;
}
