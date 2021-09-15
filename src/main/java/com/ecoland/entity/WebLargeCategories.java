package com.ecoland.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.ecoland.model.response.system.SearchWebLargeCategoryResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author Tien-ITS
 * 
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "web_large_categories")

@SqlResultSetMapping(name = "SearchWebLargeCategoryDtoMap", classes = @ConstructorResult(targetClass = SearchWebLargeCategoryResponse.class, columns = {
        @ColumnResult(name = "id", type = Integer.class), @ColumnResult(name = "categoryName") }))

public class WebLargeCategories extends CommonEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "category_name", length = 20)
    private String categoryName;

    public WebLargeCategories() {

    }
}
