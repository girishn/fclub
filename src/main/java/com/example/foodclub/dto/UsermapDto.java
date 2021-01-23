package com.example.foodclub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsermapDto {
    private String palsId;
    private String pgrId;
    private long wcsId;
    private EnrollmentDto enrollment;
    private Set<PurchaseDto> purchases;
    private Set<PromoDto> promos;
}
