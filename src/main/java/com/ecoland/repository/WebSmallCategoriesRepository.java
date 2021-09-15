package com.ecoland.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecoland.entity.WebSmallCategories;
import com.ecoland.model.response.AutoCompleteResponse;

@Repository
public interface WebSmallCategoriesRepository extends JpaRepository<WebSmallCategories, Long> {

    Optional<WebSmallCategories> findById(Integer id);

    @Query(value = "SELECT wsc.id as id, wsc.category_name as name " + "FROM web_small_categories wsc "
            + "WHERE wsc.delete_flag = 0", nativeQuery = true)
    List<AutoCompleteResponse> findByCategoryNameAutoComplete();

    Optional<WebSmallCategories> findById(Long Id);

    Optional<WebSmallCategories> findByCategoryName(String categoryName);
}
