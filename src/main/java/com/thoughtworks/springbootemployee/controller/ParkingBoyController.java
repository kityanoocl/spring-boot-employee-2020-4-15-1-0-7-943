package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-boys")
public class ParkingBoyController {
    @Autowired
    ParkingBoyService parkingBoyService;

    @GetMapping
    public List<ParkingBoy> getParkingBoys() {
        return parkingBoyService.getParkingBoys();
    }

    @PostMapping
    public ParkingBoy addParkingBoy(@RequestBody ParkingBoy parkingBoy) {
        return parkingBoyService.addParkingBoy(parkingBoy);
    }
}
