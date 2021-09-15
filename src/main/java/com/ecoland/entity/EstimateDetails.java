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
@Table(name = "estimate_details")
public class EstimateDetails extends CommonEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "estimate_id", nullable = false)
    @JsonProperty
    private Estimates estimates;

    @Column(name = "line_no")
    private Integer lineNo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_category_id", nullable = false)
    @JsonProperty
    private ProductCategory productCategory;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_rank_id", nullable = false)
    @JsonProperty
    private ProductRanks productRanks;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "receipt_type")
    private Integer receiptType;

    @Column(name = "recoverable_flag")
    private Boolean recoverableFlag;

    @Column(name = "width")
    private Integer width;

    @Column(name = "depth")
    private Integer depth;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "item_count")
    private Integer itemCount;

    @Column(name = "unit_price_no_tax")
    private Integer unitPriceNoTax;

    @Column(name = "total_price_no_tax")
    private Integer totalPriceNoTax;

    @Column(name = "web_estimate_name")
    private String webEstimateName;

    @Column(name = "web_estimate_size")
    private String webEstimateSize;

    @Column(name = "web_estimate_detail")
    private String webEstimateDetail;

    @Column(name = "sort_no")
    private Integer sortNo;

    @Column(name = "stock_id")
    private Integer stockId;

    @Column(name = "availability_flag")
    private Integer availabilityFlag;

}
