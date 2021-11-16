package com.thanh.practiceelastic.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.thanh.practiceelastic.entity.Car;

@Repository
public interface CarElasticRepositpry extends ElasticsearchRepository<Car, String> {

	public Page<Car> findByBrandAndColor(String brand, String color, Pageable pageable);
	
	public List<Car> findByFirstRealeaseDateAfter(LocalDate firstRealeaseDate);
}
