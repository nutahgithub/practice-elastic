package com.thanh.practiceelastic.api.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thanh.practiceelastic.entity.Car;
import com.thanh.practiceelastic.repository.CarElasticRepositpry;
import com.thanh.practiceelastic.service.CarService;

@RestController
@RequestMapping(value = "/api/car/v1")
public class CarApi {
	
	private static final Logger LOG = LoggerFactory.getLogger(CarApi.class);
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private CarElasticRepositpry carElasticRepositpry;
	
	@GetMapping(value = "/random", produces = MediaType.APPLICATION_JSON_VALUE)
	public Car randomCar() {
		return carService.generateCar();
	}
	
	@PostMapping(value = "/echo", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String echo(@RequestBody Car car) {
		LOG.info("Car is {}", car);
		return car.toString();
	}
	
	@GetMapping(value = "/count")
	public String countCar() {
		return "There are : " + carElasticRepositpry.count() + " cars";
	}
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String saveCar(@RequestBody Car car) {
		var id = carElasticRepositpry.save(car);
		return "Saved with ID: " + id.getId();
	}
	
	@GetMapping(value = "/{id}")
	public Car getCar(@PathVariable("id") String carId) {
		return carElasticRepositpry.findById(carId).orElse(null);
	}
	
	@PutMapping(value = "/{id}")
	public String updateCar(@PathVariable("id") String carId, @RequestBody Car car) {
		car.setId(carId);
		var newCard = carElasticRepositpry.save(car);
		return "Updated car with ID: " + newCard.getId();
	}
	
	@GetMapping(value = "/find-json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Car> findCarByBrandAndColor(@RequestBody Car car) {
		return carElasticRepositpry.findByBrandAndColor(car.getBrand(), car.getColor());
	}
	
	@GetMapping(value = "/cars/{brand}/{color}")
	public List<Car> findCarsByPath(@PathVariable String brand, @PathVariable String color) {
		return carElasticRepositpry.findByBrandAndColor(brand, color);
	}
	
	@GetMapping(value = "/cars")
	public List<Car> findCarsByParam(@RequestParam String brand, @RequestParam String color) {
		return carElasticRepositpry.findByBrandAndColor(brand, color);
	}
}
