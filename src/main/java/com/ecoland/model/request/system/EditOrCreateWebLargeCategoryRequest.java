package com.ecoland.model.request.system;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ecoland.common.Constants;

import lombok.Data;

/**
 * 
 * @author Tien-ITS
 * 
 */
@Data
public class EditOrCreateWebLargeCategoryRequest {
    private int id;

    @NotBlank(message = Constants.VALIDATE_THE_FIELD)
    @Size(max = 20, message = Constants.VALIDATE_MAX_SIZE_20)
    private String categoryName;
}
