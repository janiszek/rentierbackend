package com.kjaniszewski.RentierBackend.entity;

import com.kjaniszewski.RentierBackend.enums.PaymentType;
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
public class Payment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date date;
    private Float amount;
    @Enumerated(EnumType.ORDINAL)
    private PaymentType status;
    //set this column as NOT NULL
    @ManyToOne(optional=false)
    private Location location;
    //set this column as NOT NULL
    @ManyToOne(optional=false)
    private Tenant tenant;

    public Payment(Date date, Float amount, PaymentType status) {
        this.date = date;
        this.amount = amount;
        this.status = status;
    }
}
