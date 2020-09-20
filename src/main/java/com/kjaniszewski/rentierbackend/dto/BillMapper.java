package com.kjaniszewski.rentierbackend.dto;

import com.kjaniszewski.rentierbackend.entity.Bill;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BillMapper {
    BillDTO toBillDTO(Bill bill);
    List<BillDTO> toBillDTOs(List<Bill> bills);
    Bill toBill(BillDTO billDTO);
}
