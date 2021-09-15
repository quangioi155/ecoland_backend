package com.ecoland.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 
 * @author quannn@its-global.vn
 * 
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "outsource_companies")
public class OutsourceCompanies extends CommonEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    // join table partners
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partner_id", nullable = false)
    @JsonProperty
    private Partners partners;

    @Column(name = "outsource_company_name", length = 60)
    private String outsourceCompanyName;

    @Column(name = "outsource_company_short_name", length = 30)
    private String outsourceCompanyShortName;

    @Column(name = "outsource_company_name_kana", length = 60)
    private String outsourceCompanyNameKana;

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

}
