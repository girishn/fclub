package com.example.foodclub.controller;

import com.example.foodclub.dto.PromoDto;
import com.example.foodclub.dto.PurchaseDto;
import com.example.foodclub.model.Promo;
import com.example.foodclub.model.Purchase;
import com.example.foodclub.service.FoodClubService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
public class PromoController {

    private final ModelMapper modelMapper;
    private final FoodClubService foodClubService;

    @GetMapping("/users/{userId}/promos")
    public List<PromoDto> getPromosByUser(@PathVariable(value = "userId") Long userId) {
        return foodClubService.getPromosByUser(userId).stream()
                .map(this::convertToDto)
                .map(promoDto -> {
                    promoDto.setPurchases(foodClubService.findPurchasesByPromoId(promoDto.getId()).stream()
                            .map(this::convertToDto)
                            .collect(Collectors.toList()));
                    return promoDto;
                })
                .collect(Collectors.toList());
    }

    private PromoDto convertToDto(Promo promo) {
        return modelMapper.map(promo, PromoDto.class);
    }

    private PurchaseDto convertToDto(Purchase purchase) {
        return modelMapper.map(purchase, PurchaseDto.class);
    }
}
