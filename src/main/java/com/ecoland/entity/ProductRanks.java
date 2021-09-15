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
import com.ecoland.model.response.system.SearchRankResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @class ProductRanks
 * 
 * @entity product_ranks
 * 
 * @author ITSG - HoanNNC
 */
@Entity
@Table(name = "product_ranks")
@Data
@EqualsAndHashCode(callSuper = false)

@SqlResultSetMapping (
        name = "SearchRankDtoMap",
        classes = @ConstructorResult(
                targetClass = SearchRankResponse.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "productRankName"),
                        @ColumnResult(name = "productSize", type = Integer.class),
                        @ColumnResult(name = "weight", type = Integer.class),
                        @ColumnResult(name = "priceNotax", type = Integer.class)
                }
        )
)
public class ProductRanks extends CommonEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
   
    @Column(nullable = false, length = 30, unique = true)
    private String productRankName;
    
    @Column(nullable = false)
    private int productSize;
    
    @Column(nullable = false)
    private int weight;
    
    @Column(nullable = false)
    private int priceNotax;
}
