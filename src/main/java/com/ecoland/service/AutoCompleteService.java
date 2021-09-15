package com.ecoland.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecoland.model.response.AutoCompleteResponse;
import com.ecoland.repository.WebSmallCategoriesRepository;

@Service
public class AutoCompleteService {
    private static final Logger logger = LoggerFactory.getLogger(AutoCompleteService.class);
    @Autowired
    private WebSmallCategoriesRepository webSmallCategoriesRepository;

    public List<AutoCompleteResponse> autoCompletes() {
        logger.info("-- Get list web small category with name --");
        return webSmallCategoriesRepository.findByCategoryNameAutoComplete();
    }
}
