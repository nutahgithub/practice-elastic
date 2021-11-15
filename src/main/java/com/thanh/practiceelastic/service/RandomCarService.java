package com.thanh.practiceelastic.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.thanh.practiceelastic.entity.Car;
import com.thanh.practiceelastic.util.RandomDateUtil;

@Service
public class RandomCarService implements CarService {

	@Override
	public Car generateCar() {
		var brand = BRANDS.get(ThreadLocalRandom.current().nextInt(0, BRANDS.size()));
		var color = COLORS.get(ThreadLocalRandom.current().nextInt(0, COLORS.size()));
		var type = TYPES.get(ThreadLocalRandom.current().nextInt(0, TYPES.size()));
		
		var available = ThreadLocalRandom.current().nextBoolean();
		var price = ThreadLocalRandom.current().nextInt(5000, 12001);
		var firstReleaseDate = RandomDateUtil.generateRandomLocalDate();
		
		var randomCount = ThreadLocalRandom.current().nextInt(ADDITIONAL_FEATURES.size());
		var addtionFeatures = new ArrayList<String>();
		
		for(int i = 0; i < randomCount; i++) {
			addtionFeatures.add(ADDITIONAL_FEATURES.get(i));
		}
		
		Car result = new Car(brand, color, type);
		result.setPrice(price);
		result.setAvailble(available);
		result.setFirstRealeaseDate(firstReleaseDate);
		result.setAdditionalFeatuiures(addtionFeatures);
		
		return result;
	}

}
