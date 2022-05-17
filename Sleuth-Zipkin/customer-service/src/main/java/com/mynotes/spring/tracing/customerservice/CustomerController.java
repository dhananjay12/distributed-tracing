package com.mynotes.spring.tracing.customerservice;


import brave.baggage.BaggageField;
import brave.baggage.CorrelationScopeConfig;
import brave.context.slf4j.MDCScopeDecorator;
import brave.propagation.CurrentTraceContext;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    private static Logger log = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping(value = "/{customerId}")
    public String address(@PathVariable(name = "customerId", required = true) long customerId){
        log.info("GET /customer/"+customerId);

        return "Customer details of id="+customerId;
    }

    @GetMapping("/headers")
    @ResponseBody
    public Map<String, String> headers(@RequestHeader MultiValueMap<String, String> headers, HttpServletRequest httpServletRequest) {

        Map<String, String> map = new HashMap<>();

        headers.forEach((key, value) -> {
            log.info(String.format("Header '%s' = %s", key, value.stream().collect(Collectors.joining("|"))));
            map.put(key, value.stream().collect(Collectors.joining("|")));
        });

        return map;
    }
}

@Configuration
class BaggageConfiguration {
    @Bean
    BaggageField countryCodeField() {
        return BaggageField.create("origin");
    }

    @Bean
    CurrentTraceContext.ScopeDecorator mdcScopeDecorator() {
        return MDCScopeDecorator.newBuilder()
                .clear()
                .add(CorrelationScopeConfig.SingleCorrelationField.newBuilder(countryCodeField())
                        .flushOnUpdate()
                        .build())
                .build();
    }

}
