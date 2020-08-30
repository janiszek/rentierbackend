package com.kjaniszewski.rentierbackend.entity;

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
@Table(name = "BILLGROUP")
public class BillGroup {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Boolean fixed;

    public BillGroup(String description, Boolean fixed) {
        this.description = description;
        this.fixed = fixed;
    }
}
