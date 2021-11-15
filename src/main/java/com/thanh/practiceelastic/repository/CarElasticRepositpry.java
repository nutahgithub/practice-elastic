package com.thanh.practiceelastic.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.thanh.practiceelastic.entity.Car;

@Repository
public interface CarElasticRepositpry extends ElasticsearchRepository<Car, String> {

}
