package com.ecoland.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ecoland.model.response.system.SearchCompanyResponse;
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
@Table(name = "partners")

@SqlResultSetMapping (
        name = "SearchPartnerDtoMap",
        classes = @ConstructorResult(
                targetClass = SearchCompanyResponse.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "partnerName"),
                        @ColumnResult(name = "postalCode"),
                        @ColumnResult(name = "address"),
                        @ColumnResult(name = "tel"),
                        @ColumnResult(name = "fax")
                }
        )
)

public class Partners extends CommonEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "partner_name", length = 30, unique = true)
    private String partnerName;

    @Column(name = "main_flag")
    private Boolean mainFlag;

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

    @Column(name = "mail_address", length = 60)
    private String mailAddress;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "manager_name")
    private String managerName;

    @OneToMany(mappedBy = "partners", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @Fetch(value = FetchMode.SELECT)
    private List<Branches> branches;

    @OneToMany(mappedBy = "partners", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @Fetch(value = FetchMode.SELECT)
    private List<UserAccounts> userAccounts = new ArrayList<>();

    public Partners() {
    }
}
