package com.mynotes.spring.tracing.portalservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PortalController {

    private static Logger log = LoggerFactory.getLogger(PortalController.class);

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "/fullDetails/{customerId}")
    public String address(@PathVariable(name = "customerId", required = true) long customerId){
        log.info("GET /fullDetails/"+customerId);
        String customerResponse=restTemplate.getForObject("http://customer-service/customer/"+customerId,String.class);
        String addressResponse=restTemplate.getForObject("http://address-service/address/"+customerId,String.class);
        return customerResponse+ "<br>" +addressResponse;
    }
}
