package com.thanh.practiceelastic.common;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.thanh.practiceelastic.entity.Car;
import com.thanh.practiceelastic.repository.CarElasticRepositpry;
import com.thanh.practiceelastic.service.CarService;

@Component
public class CarElasticDatasource {
	
	private Logger LOG = LoggerFactory.getLogger(CarElasticDatasource.class);

	@Autowired
	private CarElasticRepositpry carElasticRepositpry;
	
	@Autowired
	@Qualifier("randomCarService")
	private CarService carService;
	
	@Autowired
	@Qualifier("webClientElasticsearch")
	private WebClient webClient;
	
	
	@EventListener(ApplicationReadyEvent.class)
	public void populateData() {
		var response = webClient.delete().uri("/practice-elasticsearch1").retrieve().bodyToMono(String.class).block();
		LOG.info("End delete with response {}", response);
		
		var cars = new ArrayList<Car>();
		
		for (int i = 0; i < 10_000; i++) {
			cars.add(carService.generateCar());
		}
		
		carElasticRepositpry.saveAll(cars);
		
		LOG.info("Saved {} cars", carElasticRepositpry.count());
	}
}
