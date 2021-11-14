package com.thanh.practiceelastic.api.server;

import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DefaultRestApi {
	
	private Logger LOG = LoggerFactory.getLogger(DefaultRestApi.class);

	@GetMapping(value = "/welcome")
	public String welcome() {
		LOG.info(StringUtils.join("ABC11", "Spring", "Elasticsearch"));
		return "Welcome spring boot";
	}
	
	@GetMapping(value = "/time")
	public String time() {
		return "Time:" + LocalTime.now().toString() + ", Date:" + LocalDate.now().toString();
	}
}
