package com.mynotes.spring.tracing.portalservice;

import brave.baggage.BaggageField;
import brave.baggage.CorrelationScopeConfig;
import brave.context.slf4j.MDCScopeDecorator;
import brave.propagation.CurrentTraceContext;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/baggage")
@Slf4j
public class BaggageController {

    final BaggageService baggageService;

    final Tracer tracer;

    public BaggageController(BaggageService baggageService, Tracer tracer) {
        this.baggageService = baggageService;
        this.tracer = tracer;
    }

    @GetMapping("/headers")
    public Map<String, String> test(){
        log.info("GET /baggage/headers");
        return baggageService.getCustomer();
    }
}

@Service
@Slf4j
class BaggageService {

    final RestTemplate restTemplate;

    final BaggageField origin;

    BaggageService(RestTemplate restTemplate, BaggageField origin) {
        this.restTemplate = restTemplate;
        this.origin = origin;
    }

    public Map<String, String> getCustomer() {
        origin.updateValue("From Baggage Service");
        log.info("getCustomer");
        return restTemplate.getForObject("http://customer-service/customer/headers", Map.class);
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
