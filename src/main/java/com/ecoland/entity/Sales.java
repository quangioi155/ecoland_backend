package com.ecoland.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

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
@Entity
@Table(name = "sales")
public class Sales extends CommonEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonProperty
    private Branches branches;

    @Column(name = "receipt_no")
    private String receiptNo;

    @Column(name = "sales_amount")
    private Integer salesAmount;

    @Column(name = "tax")
    private Integer tax;

    @Column(name = "branch_service_type")
    private Integer branchServiceType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id", nullable = false)
    @JsonProperty
    private UserAccounts userAccounts;

    @Column(name = "issued_at")
    private Timestamp issuedAt;

    @Column(name = "payment")
    private Integer payment;

    @Column(name = "cash_flag")
    private Integer cashFlag;

    @Column(name = "description")
    private String description;

    @Column(name = "sales_cancel_flag")
    private Integer salesCancelFlag;

    @Column(name = "r_link_client_id")
    private Integer rLinkClientId;

    @Column(name = "sales_finshed_flag")
    private Integer salesFinshedFlag;
}
