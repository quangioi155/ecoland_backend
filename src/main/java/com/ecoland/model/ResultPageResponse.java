package com.ecoland.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultPageResponse {

    private Integer totalItems;
    private List<?> items;
    private Integer totalPages;
    private Integer currentPage;
    
}
