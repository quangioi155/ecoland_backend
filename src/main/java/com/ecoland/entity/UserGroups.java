package com.ecoland.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "user_groups")
public class UserGroups extends CommonEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "group_name", length = 30)
    private String groupName;

    @Column(name = "contact_customer_flag")
    private Boolean contactCustomerFlag;

    @Column(name = "driver_flag")
    private Boolean driverFlag;

    @Column(name = "vehicle_dispatch_flag")
    private Boolean vehicleDispatchFlag;

    @Column(name = "zec_flag")
    private Boolean zecFlag;

    @Column(name = "manage_flag")
    private Boolean manageFlag;

    @Column(name = "warehouse_flag")
    private Boolean warehouseFlag;

    @Column(name = "system_flag")
    private Boolean systemFlag;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userGroups")
    @JsonIgnore
    @Fetch(value = FetchMode.SELECT)
    private List<UserAccounts> userAccounts = new ArrayList<>();

    public UserGroups() {
    }

}
