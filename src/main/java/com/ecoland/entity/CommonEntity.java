package com.ecoland.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@MappedSuperclass
@Data
public class CommonEntity {

    @Column(name = "created_at")
    @NotNull
    private Timestamp createAt;

    @Column(name = "updated_at")
    @NotNull
    private Timestamp updatedAt;

    @Column(name = "created_by", columnDefinition = "integer default 0")
    @NotNull
    private Integer createdBy;

    @Column(name = "updated_by", columnDefinition = "integer default 0")
    @NotNull
    private Integer updatedBy;

    @Column(name = "delete_flag", columnDefinition = "integer default 0")
    @NotNull
    private Integer deleteFlag;
}
