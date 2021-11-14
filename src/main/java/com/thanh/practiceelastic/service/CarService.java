package com.thanh.practiceelastic.service;

import java.util.List;

import com.thanh.practiceelastic.entity.Car;

public interface CarService {
	List<String> BRANDS = List.of("Toyota", "Honda", "Ford");
	List<String> COLORS = List.of("Red", "Yellow", "Green");
	List<String> TYPES = List.of("Sedan", "SUV", "MPV");
	
	Car generateCar();
}
