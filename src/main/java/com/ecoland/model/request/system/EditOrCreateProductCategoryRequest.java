package com.ecoland.model.request.system;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Getter
@Setter
public class EditOrCreateProductCategoryRequest {

    private Integer id;
    private String categoryName;
    private Integer pickupFeeNoTax;
    private Integer warewhousingFeeNoTax;
    private Integer warehousingTransactionFee;
    private Integer standardRankId;
    private Boolean recoverableFlag;
    private String keywords;
    private Boolean webDispFlag;
    private Integer webSmallCategoryId;
    private String imgFilePath;
    private Boolean managementOut;
}
