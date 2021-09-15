package com.ecoland.model.request;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDTO extends PaginationRequest {

    private List<Filter> filters;

    @Data
    public static final class Filter {
        private String key;
        private String values;
    }
}
