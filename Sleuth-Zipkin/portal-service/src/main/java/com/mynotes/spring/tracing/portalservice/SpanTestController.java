package com.mynotes.spring.tracing.portalservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.cloud.sleuth.annotation.TagValueResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/span")
@Slf4j
public class SpanTestController {

    final Tracer tracer;

    final SpanTestService spanTestService;

    public SpanTestController(Tracer tracer, SpanTestService spanTestService) {
        this.tracer = tracer;
        this.spanTestService = spanTestService;
    }

    @GetMapping("/{customerId}")
    public String spanTest(@PathVariable(name = "customerId") int customerId) {
        log.info("GET /span/" + customerId);
        String customerResponse = spanTestService.getCustomer(customerId);
        return customerResponse;
    }

    @GetMapping("/new-span/{customerId}")
    public String newSpan(@PathVariable(name = "customerId") int customerId) {
        log.info("GET /span/new-span/" + customerId);
        String customerResponse = spanTestService.getCustomerNewSpan(customerId);
        return customerResponse;
    }

    @GetMapping("/new-span-tag/{customerId}")
    public String newSpanTag(@PathVariable(name = "customerId") int customerId) {
        log.info("GET /span/new-span-tag/" + customerId);
        String customerResponse = spanTestService.getCustomerNewSpanTag(customerId);
        return customerResponse;
    }
}

@Service
@Slf4j
class SpanTestService {

    final RestTemplate restTemplate;

    SpanTestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getCustomer(int customerId) {
        log.info("getCustomer");
        return restTemplate.getForObject("http://customer-service/customer/" + customerId, String.class);
    }

    @NewSpan("customer-service")
    public String getCustomerNewSpan(int customerId) {
        log.info("getCustomerNewSpan");
        return restTemplate.getForObject("http://customer-service/customer/" + customerId, String.class);
    }

    @NewSpan("customer-service")
    public String getCustomerNewSpanTag(@SpanTag(key = "service-class", resolver = TagValueResolver.class) int customerId) {
        log.info("getCustomerNewSpanTag");
        return restTemplate.getForObject("http://customer-service/customer/" + customerId, String.class);
    }

}

@Configuration
class SpanConfiguration {
    @Bean(name = "myCustomTagValueResolver")
    public TagValueResolver tagValueResolver() {
        return parameter -> "Value from myCustomTagValueResolver" + parameter;
    }
}
