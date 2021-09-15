package com.ecoland.model.request.system;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ecoland.common.Constants;

import lombok.Data;

@Data
public class FormOemTypeRequest {
    private Integer id;

    @NotEmpty(message = Constants.VALIDATE_THE_FIELD)
    @Size(max = 20, message = Constants.VALIDATE_MAX_SIZE_20)
    private String oemName;

    @NotNull(message = Constants.VALIDATE_THE_FIELD)
    private int sortNo;
}
