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
@Table(name = "estimates")
public class Estimates extends CommonEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "sales_branch_id", nullable = false)
    @JsonProperty
    private Branches branches;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "partner_id", nullable = false)
    @JsonProperty
    private Partners partners;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "customer_last_name")
    private String customerLastName;

    @Column(name = "customer_first_name")
    private String customerFirstName;

    @Column(name = "customer_last_name_kana")
    private String customerLastNameKana;

    @Column(name = "customer_first_name_kana")
    private String customerFirstNameKana;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "prefecture")
    private String prefecture;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "building")
    private String building;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longtitude")
    private String longtitude;

    @Column(name = "building_type")
    private Integer buildingType;

    @Column(name = "elevator_flag")
    private Boolean elevatorFlag;

    @Column(name = "living_floor")
    private Integer livingFloor;

    @Column(name = "building_floor")
    private Integer buildingFloor;

    @Column(name = "tel1")
    private String tel1;

    @Column(name = "tel2")
    private String tel2;

    @Column(name = "fax")
    private String fax;

    @Column(name = "mail_address1")
    private String mailAddress1;

    @Column(name = "mail_address2")
    private String mailAddress2;

    @Column(name = "pickup_preffered_date1")
    private Date pickupPrefferedDate1;

    @Column(name = "pickup_preffered_time1")
    private Integer pickupPrefferedTime1;

    @Column(name = "pickup_preffered_date2")
    private Date pickupPrefferedDate2;

    @Column(name = "pickup_preffered_time2")
    private Integer pickupPrefferedTime2;

    @Column(name = "pickup_preffered_date3")
    private Date pickupPrefferedDate3;

    @Column(name = "pickup_preffered_time3")
    private Integer pickupPrefferedTime3;

    @Column(name = "payment")
    private Integer payment;

    @Column(name = "onsite_paid_amount")
    private Integer onsitePaidAmount;

    @Column(name = "advance_payment_flag")
    private Boolean advancePaymentFlag;

    @Column(name = "payment_confirmed_date")
    private Date paymentConfirmedDate;

    @Column(name = "estimate_charge_id")
    private Integer estimateChargeId;

    @Column(name = "estimate_charge")
    private String estimateCharge;

    @Column(name = "service_type")
    private Integer serviceType;

    @Column(name = "oem_type")
    private Integer oemType;

    @Column(name = "pre_call_memo")
    private String preCallMemo;

    @Column(name = "pre_call_result_flag")
    private Integer preCallResultFlag;

    @Column(name = "contact_type")
    private Integer contactType;

    @Column(name = "collect_carry_flag")
    private Boolean collectCarryFlag;

    @Column(name = "working_flag")
    private Boolean workingFlag;

    @Column(name = "receipt_name")
    private String receiptName;

    @Column(name = "industrial_name")
    private String industrialName;

    @Column(name = "industrial_postal_code")
    private String industrialPostalCode;

    @Column(name = "industrial_prefecture")
    private String industrialPrefecture;

    @Column(name = "industrial_city")
    private String industrialCity;

    @Column(name = "industrial_address")
    private String industrialAddress;

    @Column(name = "industrial_building")
    private String industrialBuilding;

    @Column(name = "media")
    private Integer media;

    @Column(name = "birth_day")
    private Date birthDay;

    @Column(name = "job")
    private String job;

    @Column(name = "method_of_identity")
    private Integer methodOfIdentity;

    @Column(name = "identity_no")
    private String identityNo;

    @Column(name = "callcenter_inquire_no")
    private String callcenterInquireNo;

    @Column(name = "introduction_id")
    private Integer introductionId;

    @Column(name = "introduction_name")
    private String introductionName;

    @Column(name = "introduction_site_id")
    private Integer introductionSiteId;

    @Column(name = "introduction_site_name")
    private String introductionSiteName;

    @Column(name = "introduction_charge_name")
    private String introductionChargeName;

    @Column(name = "introduction_tel1")
    private String introductionTel1;

    @Column(name = "introduction_tel2")
    private String introductionTel2;

    @Column(name = "introduction_mail")
    private String introductionMail;

    @Column(name = "description")
    private String description;

    @Column(name = "estimate_description")
    private String estimateDescription;

    @Column(name = "estimate_status")
    private Integer estimateStatus;

    @Column(name = "finished_time")
    private Timestamp finishedTime;

    @Column(name = "finished_payment")
    private Integer finishedPayment;

    @Column(name = "finished_payment_with_tax")
    private Integer finishedPaymentWithTax;

    @Column(name = "product_eco_total_price")
    private Integer productEcoTotalPrice;

    @Column(name = "product_eco_total_price_with_tax")
    private Integer productEcoTotalPriceWithTax;

    @Column(name = "buying_eco_total_price")
    private Integer buyingEcoTotalPrice;

    @Column(name = "buying_eco_total_price_with_tax")
    private Integer buyingEcoTotalPriceWithTax;

    @Column(name = "basic_price")
    private Integer basicPrice;

    @Column(name = "basic_with_tax")
    private Integer basicWithTax;

    @Column(name = "option_total_price")
    private Integer optionTotalPrice;

    @Column(name = "option_total_price_with_tax")
    private Integer optionTotalPriceWithTax;

    @Column(name = "outsource_total_price")
    private Integer outsourceTotalPrice;

    @Column(name = "outsource_total_price_with_tax")
    private Integer outsourceTotalPriceWithTax;

    @Column(name = "discount_total_price")
    private Integer discountTotalPrice;

    @Column(name = "discount_total_price_with_tax")
    private Integer discountTotalPriceWithTax;

    @Column(name = "sign_pickup_flag")
    private Integer signPickupFlag;

    @Column(name = "sign_purchase_flag")
    private Integer signPurchaseFlag;

    @Column(name = "sign_finished_registered_by")
    private Integer signFinishedRegisteredBy;

    @Column(name = "buying_sign_registered_by")
    private Integer buyingSignRegisteredBy;

    @Column(name = "problem_flag")
    private Integer problemFlag;

    @Column(name = "ecopass")
    private String ecopass;

    @Column(name = "receipt_send_type")
    private Integer receiptSendType;

    @Column(name = "receipt_send_flag")
    private Integer receiptSendFlag;

    @Column(name = "ordered_by")
    private Integer orderedBy;

    @Column(name = "finished_by")
    private Integer finishedBy;

    @Column(name = "is_calculate_flag")
    private Integer isCalculateFlag;

    @Column(name = "cb_finished_date")
    private Date cbFinishedDate;
}
