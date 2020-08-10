package com.kjaniszewski.RentierBackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(length=30, unique=true, nullable=false)
    private String shortName;

    @OneToOne(optional=false, cascade = CascadeType.ALL)
    private TenantDetails details;

    public Tenant(String shortName) {
        this.shortName = shortName;
    }

    public Tenant(String shortName, TenantDetails tenantDetails) {
        this.shortName = shortName;
        this.setDetails(tenantDetails);
    }
}
