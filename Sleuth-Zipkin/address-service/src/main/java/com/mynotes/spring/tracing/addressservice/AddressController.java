package com.mynotes.spring.tracing.addressservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/address")
public class AddressController {

    private static Logger log = LoggerFactory.getLogger(AddressController.class);

    @GetMapping(value = "/{customerId}")
    public String address(@PathVariable(name = "customerId", required = true) long customerId){
        log.info("GET /address/"+customerId);
        return "Address of id="+customerId;
    }
}
