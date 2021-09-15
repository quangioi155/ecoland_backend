package com.ecoland.model.request.system;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ecoland.common.Constants;

import lombok.Data;

/**
 * @class FormRankRequest
 * 
 * @summary request create/edit rank
 * 
 * @author ITSG - HoanNNC
 */
@Data
public class FormRankRequest {
    private Integer id;
    
    @NotEmpty(message = Constants.VALIDATE_THE_FIELD)
    @Size(max = 30, message = Constants.VALIDATE_MAX_SIZE_30)
    private String productRankName;
    @NotNull(message = Constants.VALIDATE_THE_FIELD)
    private Integer size;
    @NotNull(message = Constants.VALIDATE_THE_FIELD)
    private Integer weight;
    @NotNull(message = Constants.VALIDATE_THE_FIELD)
    private Integer priceNotax;
}
