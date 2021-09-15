package com.ecoland.model.request;

import com.ecoland.common.Constants;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class EditOrCreateWebSmallCateRequest {

    private Integer id;

    @NotNull(message = Constants.VALIDATE_THE_FIELD)
    private Integer largeCategoryId;

    @NotBlank(message = Constants.VALIDATE_THE_FIELD)
    @Size(max = 20, message = Constants.VALIDATE_MAX_SIZE_20)
    private String categoryName;
}
