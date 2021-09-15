package com.ecoland.model.request.system;

import com.ecoland.model.request.PaginationRequest;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Tien-ITS
 * 
 */
@Getter
@Setter
public class SearchWebLargeCategoryRequest extends PaginationRequest {
    private int id;
    private String categoryName;
}
