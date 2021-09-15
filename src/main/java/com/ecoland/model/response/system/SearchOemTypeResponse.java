package com.ecoland.model.response.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchOemTypeResponse {
    private int id;
    private String oemName;
    private int sortNo;
}
