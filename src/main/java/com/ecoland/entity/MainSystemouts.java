package com.ecoland.entity;

import static javax.persistence.GenerationType.IDENTITY;

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

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "main_systemouts")
public class MainSystemouts extends CommonEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "main_system_projects_id")
    private Integer mainSystemProjectsId;

    @Column(name = "handle_date")
    private Date handleDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonProperty
    private Branches branches;

    @Column(name = "query_type")
    private String queryType;

    @Column(name = "project_code")
    private String projectCode;

    @Column(name = "business_code")
    private String businessCode;

    @Column(name = "main_code")
    private String mainCode;

    @Column(name = "main_work_type")
    private String mainWorkType;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "service_code")
    private String serviceCode;

    @Column(name = "employee_code")
    private String employeeCode;

    @Column(name = "record_type")
    private String recordType;

    @Column(name = "line_no")
    private Integer lineNo;

    @Column(name = "driver_code1")
    private String driverCode1;

    @Column(name = "truck_code1")
    private String truckCode1;

    @Column(name = "cash_flag")
    private Integer cashFlag;

    @Column(name = "sales_business_code")
    private String salesBusinessCode;

    @Column(name = "sales_account_code")
    private String salesAccountCode;

    @Column(name = "sales_name")
    private String salesName;

    @Column(name = "sales_unit_price_wo_tax")
    private Integer salesUnitPriceWoTax;

    @Column(name = "sales_unit_price_no_tax")
    private Integer salesUnitPriceNoTax;

    @Column(name = "sales_count")
    private Integer salesCount;

    @Column(name = "sales_unit")
    private String salesUnit;

    @Column(name = "sales_date")
    private Date salesDate;

    @Column(name = "sales_memo")
    private String salesMemo;

    @Column(name = "paid_business_code")
    private String paidBusinessCode;

    @Column(name = "paid_account_code")
    private String paidAccountCode;

    @Column(name = "paid_name")
    private String paidName;

    @Column(name = "paid_unit_price_wo_tax")
    private Integer paidUnitPriceWoTax;

    @Column(name = "paid_unit_price_no_tax")
    private Integer paidUnitPriceNoTax;

    @Column(name = "paid_count")
    private Integer paidCount;

    @Column(name = "paid_unit")
    private Integer paidUnit;

    @Column(name = "paid_date")
    private Date paidDate;

    @Column(name = "paid_memo")
    private String paidMemo;

    @Column(name = "business_name")
    private String businessName;
}
