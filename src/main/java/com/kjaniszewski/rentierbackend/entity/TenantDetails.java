package com.kjaniszewski.rentierbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TenantDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String pesel;
    private String documentId;
    private String address;
    private String email;
    private String phone;
    private String description;

    public TenantDetails(String firstName, String lastName, String pesel, String documentId, String address, String email, String phone, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.documentId = documentId;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.description = description;
    }
}
