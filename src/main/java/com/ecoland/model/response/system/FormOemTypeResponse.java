package com.ecoland.model.response.system;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @class FormOemTypeResponse
 * 
 * @summary Response detail oem type
 * 
 * @author ITSG - HoanNNC
 */
@Data
@NoArgsConstructor
public class FormOemTypeResponse {
    private int id;
    private String oemName;
    private int sortNo;
}
