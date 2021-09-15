package com.ecoland.model.request;

import lombok.Data;

@Data
public class WebSmallCategoryRequest extends PaginationRequest {
    private Long id;
    private String categoryName;

}
