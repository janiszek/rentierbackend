package com.kjaniszewski.RentierBackend.entity;

import com.kjaniszewski.RentierBackend.enums.BillType;
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
public class Bill {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date date;
    private Float amount;
    @Enumerated(EnumType.ORDINAL)
    private BillType status;
    //set this column as NOT NULL; in ManyToOne cascade SHOULDN'T BE used
    @ManyToOne(optional=false/*, cascade = CascadeType.ALL*/)
    private Location location;
    //set this column as NOT NULL
    @ManyToOne(optional=false/*, cascade = CascadeType.ALL*/)
    private BillGroup billGroup;

    public Bill(Date date, Float amount, BillType status) {
        this.date = date;
        this.amount = amount;
        this.status = status;
    }
}
