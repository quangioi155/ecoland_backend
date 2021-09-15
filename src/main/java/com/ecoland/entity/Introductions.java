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

import com.ecoland.model.response.system.SearchIntroductionResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @class Introductions
 * 
 * @entity introductions
 * 
 * @author ITSG - HoanNNC
 */
@Entity
@Table(name = "introductions")
@Data
@EqualsAndHashCode(callSuper = false)
@SqlResultSetMapping (
        name = "SearchIntroductionDtoMap",
        classes = @ConstructorResult(
                targetClass = SearchIntroductionResponse.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "introductionName"),
                        @ColumnResult(name = "payTiming", type = Integer.class),
                        @ColumnResult(name = "payUnit", type = Integer.class),
                        @ColumnResult(name = "payAmount"),
                        @ColumnResult(name = "discountUnit", type = Integer.class),
                        @ColumnResult(name = "discount"),
                        @ColumnResult(name = "sortNo", type = Integer.class),
                }
        )
)
public class Introductions extends CommonEntity{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id; 
    
    @Column(name = "introduction_name", length = 60, unique = true)
    private String introductionName; 
    
    @Column(name = "introduction_short_name", length = 30)
    private String introductionShortName;
    
    @Column(name = "introduction_name_kana", length = 60)
    private String introductionNameKana;
    
    @Column(name = "postal_code", length = 7)
    private String postalCode;
    
    @Column(name = "address1", length = 60)
    private String address1;
    
    @Column(name = "address2", length = 60)
    private String address2;
    
    @Column(name = "address3", length = 60)
    private String address3;
    
    @Column(name = "tel", length = 13)
    private String tel;
    
    @Column(name = "fax", length = 13)
    private String fax;
    
    @Column(name = "pay_timing", columnDefinition = "integer default 1")
    private Integer payTiming;
    
    @Column(name = "pay_unit", columnDefinition = "integer default 1")
    private Integer payUnit;
    
    @Column(name = "pay_amount")
    private Integer payAmount;
    
    @Column(name = "pay_percent", precision = 5, scale = 2)
    private Double payPercent;
    
    @Column(name = "discount_unit", columnDefinition = "integer default 1")
    private Integer discountUnit;
    
    @Column(name = "discount")
    private Integer discount;
    
    @Column(name = "discount_percent", precision = 5, scale = 2)
    private Double discountPercent;
    
    @Column(name = "sort_no")
    private Integer sortNo;
}


