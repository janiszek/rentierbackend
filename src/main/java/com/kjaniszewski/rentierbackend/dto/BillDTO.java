package com.kjaniszewski.rentierbackend.dto;

import com.kjaniszewski.rentierbackend.enums.BillType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {
    private Long id;
    private Date date;
    private BigDecimal amount;
    private BillType status;
    /*private Location location;
    private BillGroup billGroup;*/
}
