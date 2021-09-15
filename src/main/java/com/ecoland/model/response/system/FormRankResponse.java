package com.ecoland.model.response.system;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @class FormRankResponse
 * 
 * @summary Response detail product rank
 * 
 * @author ITSG - HoanNNC
 */
@Data
@NoArgsConstructor
public class FormRankResponse {
    private Integer id;
    private String productRankName;
    private Integer size;
    private Integer weight;
    private Integer priceNotax;
}
