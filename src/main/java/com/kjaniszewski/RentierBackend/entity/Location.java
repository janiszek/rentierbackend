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
public class Location {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(length=30, unique=true, nullable=false)
    private String shortName;
    private String address;
    private String photoRef;
    /*@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    private Set<Contract> contractSet = new HashSet<>();*/

    public Location(String shortName, String address, String photoRef) {
        this.shortName = shortName;
        this.address = address;
        this.photoRef = photoRef;
    }
}
