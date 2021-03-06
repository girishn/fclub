package com.example.foodclub.controller;

import com.example.foodclub.dto.UsermapDto;
import com.example.foodclub.model.UserKey;
import com.example.foodclub.model.Usermap;
import com.example.foodclub.repository.UsermapRepository;
import com.example.foodclub.service.FoodClubService;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
class UsermapController {

    private final UsermapRepository usermapRepository;
    private final ModelMapper modelMapper;
    private final FoodClubService foodClubService;

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/users")
    List<UsermapDto> all() {
        return Lists.newArrayList(usermapRepository.findAll()).stream().map(this::convertToDto).collect(Collectors.toList());
    }
    // end::get-aggregate-root[]

    @PostMapping(value = "/users")
    Usermap newUsermap(@RequestBody Usermap newUsermap) {
        return foodClubService.saveUserData(newUsermap);
    }

    // Single item

    @GetMapping("/users/{id}")
    UsermapDto one(@PathVariable Long id) {

        return convertToDto(usermapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id.toString())));
    }

    @PutMapping("/users/{id}")
    Usermap replaceUsermap(@RequestBody UsermapDto usermapDto, @PathVariable Long id) {

        return usermapRepository.findById(id)
                .map(usermap -> {
                    UserKey userKey = new UserKey(usermapDto.getPalsId(), usermapDto.getPgrId(), usermapDto.getWcsId());
                    usermap.addUserKey(userKey);
                    return usermapRepository.save(usermap);
                })
                .orElseGet(() -> usermapRepository.save(convertToObj(usermapDto)));
    }

    @DeleteMapping("/users/{id}")
    void deleteUsermap(@PathVariable Long id) {
        usermapRepository.deleteById(id);
    }

    private UsermapDto convertToDto(Usermap usermap) {
        return modelMapper.map(usermap, UsermapDto.class);
    }

    private Usermap convertToObj(UsermapDto usermapDto) {
        return modelMapper.map(usermapDto, Usermap.class);
    }
}