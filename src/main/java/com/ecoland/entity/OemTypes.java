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

import com.ecoland.model.response.system.SearchOemTypeResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @class OemTypes
 * 
 * @entity oem_types
 * 
 * @author ITSG - HoanNNC
 */
@Entity
@Table(name = "oem_types")
@Data
@EqualsAndHashCode(callSuper = false)
@SqlResultSetMapping (
        name = "SearchOemTypeDtoMap",
        classes = @ConstructorResult(
                targetClass = SearchOemTypeResponse.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "oemName"),
                        @ColumnResult(name = "sortNo", type = Integer.class)
                }
        )
)
public class OemTypes extends CommonEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 20, unique = true)
    private String oemName;
    
    @Column(nullable = false)
    private int sortNo;
}
