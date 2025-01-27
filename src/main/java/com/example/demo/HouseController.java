package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HouseController {
    private final House house;

    @Autowired
    public HouseController(House house) {
        this.house = house;
    }

    @GetMapping("/house")
    public House getHouse() {
        house.setDirection("123 Default Direction");
        return house;
    }
}
