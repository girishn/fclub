package com.example.foodclub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromoDto {

    private long id;

    private String promoName;

    private LocalDate issueDate;

    private LocalDate expirationDate;

    private String couponCode;

    private String couponStatus;

    private List<PurchaseDto> purchases;

}
