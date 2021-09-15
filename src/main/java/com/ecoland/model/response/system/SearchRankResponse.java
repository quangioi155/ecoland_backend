package com.ecoland.model.response.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRankResponse {
    private int id;
    private String productRankName;
    private int productSize;
    private int weight;
    private int priceNotax;
}
