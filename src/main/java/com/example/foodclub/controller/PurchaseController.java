package com.example.foodclub.controller;

import com.example.foodclub.dto.PurchaseDto;
import com.example.foodclub.error.ResourceNotFoundException;
import com.example.foodclub.model.Purchase;
import com.example.foodclub.service.FoodClubService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
public class PurchaseController {

    private final ModelMapper modelMapper;
    private final FoodClubService foodClubService;

    @GetMapping("/users/{userId}/purchases")
    public List<PurchaseDto> getPurchasesByUser(@PathVariable(value = "userId") Long userId) {
        return foodClubService.getPurchasesByUser(userId).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @PostMapping("/users/{userId}/purchases")
    public PurchaseDto createPurchase(@PathVariable(value = "userId") Long userId,
                               @Valid @RequestBody PurchaseDto purchaseDto) throws ResourceNotFoundException {
        return convertToDto(foodClubService.createPurchase(userId, convertToObj(purchaseDto)));
    }

    private Purchase convertToObj(PurchaseDto purchaseDto) {
        return modelMapper.map(purchaseDto, Purchase.class);
    }

    private PurchaseDto convertToDto(Purchase purchase) {
        return modelMapper.map(purchase, PurchaseDto.class);
    }

}
