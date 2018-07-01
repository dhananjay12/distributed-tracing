package com.mynotes.microservice.service1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Service1Controller {
	
	@Autowired
	Service2Feign service2Feign;

	@Autowired
	RestTemplate restTemplate;
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(method = RequestMethod.GET, value = "/getStaff")
	public ResponseEntity<?> getStaff() {
		logger.info("In service 1 getStaff");
		String response = (String) restTemplate.getForObject("http://localhost:8050/employees", String.class);

		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getStaff/{id}")
	public ResponseEntity<?> getStaffMember(@PathVariable(name="id")long id) {
		logger.info("In service 1 getStaffMember");
		String response = service2Feign.getEmployee(id);

		return ResponseEntity.ok(response);
	}

}
