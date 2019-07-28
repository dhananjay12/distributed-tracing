package com.mynotes.spring.tracing.customerservice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    private static Logger log = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping(value = "/{customerId}")
    public String address(@PathVariable(name = "customerId", required = true) long customerId){
        log.info("GET /customer/"+customerId);
        return "Customer details of id="+customerId;
    }
}
