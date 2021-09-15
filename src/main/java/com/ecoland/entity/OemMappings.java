package com.ecoland.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Entity
@Table(name = "oem_mappings")
public class OemMappings extends CommonEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "oem_type_id")
    private Integer oemTypeId;

    @Column(name = "another_category_name")
    private String anotherCategoryName;

    @Column(name = "another_pickup_fee")
    private Integer anotherPickupFee;

    @Column(name = "another_warewhousing_fee")
    private Integer anotherWarewhousingFee;

    @Column(name = "another_transaction_fee")
    private Integer anotherTransactionFee;

    @Column(name = "oem_categories_cd")
    private String oemCategoriesCd;

    @Column(name = "keywords")
    private String keywords;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_category_id", nullable = false)
    @JsonProperty
    private ProductCategory productCategory;
}
