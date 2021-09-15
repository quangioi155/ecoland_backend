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
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@Entity
@Table(name = "product_categories")
public class ProductCategory extends CommonEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "pickup_fee_no_tax")
    private Integer pickupFeeNoTax;

    @Column(name = "warehousing_fee_no_tax")
    private Integer warehousingFeeNoTax;

    @Column(name = "warehousing_transaction_fee")
    private Integer warehousingTransactionFee;

    @Column(name = "recoverable_flag")
    private Boolean recoverableFlag;

    @Column(name = "keywords")
    private String keywords;

    @Column(name = "web_disp_flag")
    private Boolean webDispFlag;

    @Column(name = "web_small_category_id")
    private Integer webSmallCategoryId;

    @Column(name = "management_out")
    private Boolean managementOut;

    @Column(name = "img_file_path")
    private String imgFilePath;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "standard_rank_id", nullable = false)
    @JsonProperty
    private ProductRanks productRanks;
}
