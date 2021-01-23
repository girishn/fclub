package com.example.foodclub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseDto {

    private LocalDate purchaseDate;

    private long skuId;

    private String skuName;

    private double skuPrice;

}
