package com.ecoland.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "branches")
public class Branches extends CommonEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "partner_id", nullable = false)
    @JsonProperty
    private Partners partners;

    @Column(name = "branch_name", length = 30)
    private String branchName;

    @Column(name = "branch_short_name", length = 20)
    private String branchShortName;

    @Column(name = "address1", length = 60)
    private String address1;

    @Column(name = "address2", length = 60)
    private String address2;

    @Column(name = "address3", length = 60)
    private String address3;

    @Column(name = "postal_code", length = 7)
    private String postalCode;

    @Column(name = "tel", length = 13)
    private String tel;

    @Column(name = "fax", length = 13)
    private String fax;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "input_corp_site")
    private Integer inputCorpSite;

    @Column(name = "deliv_corp_site")
    private Integer delivCorpSite;

    @Column(name = "branch_code", length = 7)
    private String branchCode;

    @OneToMany(mappedBy = "branches", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @Fetch(value = FetchMode.SELECT)
    private List<UserAccounts> userAccounts = new ArrayList<>();

    public Branches() {

    }

}
