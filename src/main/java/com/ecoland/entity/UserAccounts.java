package com.ecoland.entity;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;

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
@Table(name = "user_accounts")
public class UserAccounts extends CommonEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "login_id", length = 20)
    @NotNull
    private String loginId;

    @Column(name = "login_password", length = 20)
    @NotNull
    private String loginPassword;

    @Column(name = "account_name", length = 30)
    @NotNull
    private String accountName;

    @Column(name = "account_name_kana", length = 30)
    @NotNull
    private String accountNameKana;

    // join table partners
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partner_id", nullable = false)
    @JsonProperty
    private Partners partners;

    // join table branches
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonProperty
    private Branches branches;

    // join table user_groups
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_group_id", nullable = false)
    @JsonProperty
    private UserGroups userGroups;

    @Column(name = "employee_cd", length = 30)
    private String employeeCd;

    @Column(name = "description")
    private String description;

    public UserAccounts() {
    }

}
