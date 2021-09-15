package com.ecoland.model.request;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Data
public class PageRequest {
    private Long totalItems;
    private Integer totalPages;
    private Integer currentPage;
    private List<?> items;
}
