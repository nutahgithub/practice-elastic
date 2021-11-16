package com.thanh.practiceelastic.api.server;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thanh.practiceelastic.api.response.ErrorResponse;
import com.thanh.practiceelastic.entity.Car;
import com.thanh.practiceelastic.exception.IllegalApiParamException;
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
	public List<Car> findCarByBrandAndColor(@RequestBody Car car,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		var pageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "price"));
		return carElasticRepositpry.findByBrandAndColor(car.getBrand(), car.getColor(), pageable).getContent();
	}
	
	@GetMapping(value = "/cars/{brand}/{color}")
	public ResponseEntity<Object> findCarsByPath(@PathVariable String brand,
			@PathVariable String color,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		var header = new HttpHeaders();
		header.add(HttpHeaders.SERVER, "spring");
		header.add("X-Custom-Header", "Custom Response hihi");
		
		if(StringUtils.isNumeric(color)) {
			ErrorResponse errorResponse = new ErrorResponse("Invalid color", LocalDateTime.now());
			return new ResponseEntity<Object>(errorResponse, header, HttpStatus.BAD_REQUEST);
		}
		var pageable = PageRequest.of(page, size);
		var cars = carElasticRepositpry.findByBrandAndColor(brand, color, pageable).getContent();
		return ResponseEntity.ok().headers(header).body(cars);
	}
	
	@GetMapping(value = "/cars")
	public List<Car> findCarsByParam(@RequestParam String brand,
			@RequestParam String color,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		if(StringUtils.isNumeric(color)) {
			throw new IllegalArgumentException("Invalid color: " + color);
		}
		
		if(StringUtils.isNumeric(brand)) {
			throw new IllegalApiParamException("Invalid brand: " + brand);
		}
		var pageable = PageRequest.of(page, size);
		return carElasticRepositpry.findByBrandAndColor(brand, color, pageable).getContent();
	}
	
	@GetMapping(value = "/cars/date")
	public List<Car> findCarsReleasedAfter(
			@RequestParam(name = "first_realease_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate firstRealeaseDate) {
		return carElasticRepositpry.findByFirstRealeaseDateAfter(firstRealeaseDate);
	}
	
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	private ResponseEntity<ErrorResponse> handlleInvalidColorException(IllegalArgumentException e) {
		var message = "Exception, " + e.getMessage();
		LOG.warn(message);
		
		ErrorResponse errorResponse = new ErrorResponse(message, LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	@ExceptionHandler(value = IllegalApiParamException.class)
	private ResponseEntity<ErrorResponse> handlleInvalidBrandException(IllegalApiParamException e) {
		var message = "Exception Param, " + e.getMessage();
		LOG.warn(message);
		
		ErrorResponse errorResponse = new ErrorResponse(message, LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
}
