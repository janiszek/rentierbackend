package com.kjaniszewski.RentierBackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date dateContract;
    @Temporal(TemporalType.DATE)
    private Date dateFrom;
    @Temporal(TemporalType.DATE)
    private Date dateTo;
    //set this column as NOT NULL
    @ManyToOne(optional=false)
    private Location location;
    //set this column as NOT NULL
    @ManyToOne(optional=false)
    private Tenant tenant;

    public Contract(Date dateContract, Date dateFrom, Date dateTo) {
        this.dateContract = dateContract;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

}
