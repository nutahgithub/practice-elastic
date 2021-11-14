package com.thanh.practiceelastic.api.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thanh.practiceelastic.entity.Car;
import com.thanh.practiceelastic.service.CarService;

@RestController
@RequestMapping(value = "/api/car/v1")
public class CarApi {
	@Autowired
	private CarService carService;
	
	@GetMapping(value = "/random", produces = MediaType.APPLICATION_JSON_VALUE)
	public Car randomCar() {
		return carService.generateCar();
	}
}
