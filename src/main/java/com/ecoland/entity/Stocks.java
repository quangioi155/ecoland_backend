package com.ecoland.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.Date;

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
public class Stocks extends CommonEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "estimate_id", nullable = false)
    @JsonProperty
    private Estimates estimates;

    @Column(name = "stock_code")
    private String stockCode;

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

    @Column(name = "stock_depth")
    private Integer stockDepth;

    @Column(name = "stock_width")
    private Integer stockWidth;

    @Column(name = "stock_height")
    private Integer stockHeight;

    @Column(name = "stock_size")
    private Integer stockSize;

    @Column(name = "stock_weight")
    private Integer stockWeight;

    @Column(name = "purchase_flag")
    private Integer purchaseFlag;

    @Column(name = "recoverable_flag")
    private Integer recoverableFlag;

    @Column(name = "receipt_type")
    private Integer receiptType;

    @Column(name = "oem_send_flag")
    private Integer oemSendFlag;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "estimate_detail_id", nullable = false)
    @JsonProperty
    private EstimateDetails estimateDetails;

    @Column(name = "is_returned_flag")
    private Integer isReturnedFlag;

    @Column(name = "return_execute_date")
    private Date returnExecuteDate;

    @Column(name = "reuse_kind")
    private Integer reuseKind;

    @Column(name = "reuse_start_at")
    private Timestamp reuseStartAt;

    @Column(name = "reuse_start_by")
    private Integer reuseStartBy;

    @Column(name = "reuse_finish_at")
    private Timestamp reuseFinishAt;

    @Column(name = "reuse_finish_by")
    private Integer reuseFinishBy;

    @Column(name = "reuse_result")
    private Integer reuseResult;

    @Column(name = "calcurate_flag")
    private Integer calcurateFlag;

    @Column(name = "exist_location_id")
    private Integer existLocationId;

    @Column(name = "input_date")
    private Timestamp inputDate;

}
