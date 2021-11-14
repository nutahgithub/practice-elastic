package com.thanh.practiceelastic.api.server;

import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DefaultRestApi {

	@GetMapping(value = "/welcome")
	public String welcome() {
		System.out.println(StringUtils.join("ABCddd", "Spring", "Elasticsearch"));
		return "Welcome spring boot";
	}
	
	@GetMapping(value = "/time")
	public String time() {
		return "Time:" + LocalTime.now().toString() + ", Date:" + LocalDate.now().toString();
	}
}
