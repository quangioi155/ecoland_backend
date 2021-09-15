package com.ecoland.model.response.system;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchProductCategoryResponse {

    private Integer id;
    private String categoryName;
    private Integer warehousingTransactionFee;
    private String productRankName;
    private Boolean recoverabeFlag;
    private Boolean webDispFlag;
    private String keywords;
}
