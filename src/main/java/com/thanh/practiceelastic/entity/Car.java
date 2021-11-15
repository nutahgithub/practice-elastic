package com.thanh.practiceelastic.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

// lam cho ten lac da thanh dau gay duoi
@Document(indexName = "practice-elasticsearch1")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(value = {"colorr", "name"})
public class Car {
	
	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// Neu gia tri null -> xoa item nay luon
	@JsonInclude(value = Include.NON_EMPTY)
	private List<String> additionalFeatuiures;

	// Khong tra ve item nay
	@JsonIgnore
	private boolean availble;

	private String brand;
	
	private String color;
	
	// Doi format
	@Field(type = FieldType.Date, format = DateFormat.date)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
	private LocalDate firstRealeaseDate;
	
	// Doi ten key cua item
	@JsonProperty(value = "pricee")
	private int price;
	
	private String type;
	
	private String colorr = "red";
	
	private String name ="AAA";
	
	public Car() {}

	public String getColorr() {
		return colorr;
	}

	public void setColorr(String colorr) {
		this.colorr = colorr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Car(String brand, String color, String type) {
		super();
		this.brand = brand;
		this.color = color;
		this.type = type;
	}

	public List<String> getAdditionalFeatuiures() {
		return additionalFeatuiures;
	}

	public String getBrand() {
		return brand;
	}

	public String getColor() {
		return color;
	}

	public LocalDate getFirstRealeaseDate() {
		return firstRealeaseDate;
	}

	public int getPrice() {
		return price;
	}

	public String getType() {
		return type;
	}

	public boolean isAvailble() {
		return availble;
	}

	public void setAdditionalFeatuiures(List<String> additionalFeatuiures) {
		this.additionalFeatuiures = additionalFeatuiures;
	}

	public void setAvailble(boolean availble) {
		this.availble = availble;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setFirstRealeaseDate(LocalDate firstRealeaseDate) {
		this.firstRealeaseDate = firstRealeaseDate;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "Car [availble=" + availble + ", brand=" + brand + ", color=" + color + ", firstRealeaseDate="
				+ firstRealeaseDate + ", price=" + price + ", type=" + type + "]";
	}
}
