package com.example.foodclub.controller;

import com.example.foodclub.dto.EnrollmentDto;
import com.example.foodclub.error.ResourceNotFoundException;
import com.example.foodclub.model.FoodClubEnrollment;
import com.example.foodclub.service.FoodClubService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
public class EnrollmentController {

    private final ModelMapper modelMapper;
    private final FoodClubService foodClubService;

    @GetMapping("/users/{userId}/enrollment")
    public EnrollmentDto getEnrollmentByUser(@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException {
        return convertToDto(foodClubService.getEnrollmentByUser(userId));
    }

    @PostMapping("/users/{userId}/enrollment")
    public EnrollmentDto createPurchase(@PathVariable(value = "userId") Long userId,
                                        @Valid @RequestBody EnrollmentDto enrollmentDto) throws ResourceNotFoundException {
        return convertToDto(foodClubService.createEnrollment(userId, convertToObj(enrollmentDto)));
    }

    private FoodClubEnrollment convertToObj(EnrollmentDto enrollmentDto) {
        return modelMapper.map(enrollmentDto, FoodClubEnrollment.class);
    }

    private EnrollmentDto convertToDto(FoodClubEnrollment foodClubEnrollment) {
        return modelMapper.map(foodClubEnrollment, EnrollmentDto.class);
    }

}
