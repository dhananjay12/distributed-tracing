package com.mynotes.microservice.service1;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "Service2Feign", url = "http://localhost:8050")
public interface Service2Feign {
	
	
	@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	public String getEmployee(@PathVariable("id") long id);
}
