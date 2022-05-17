package com.mynotes.spring.tracing.portalservice;

import brave.baggage.BaggageField;
import brave.baggage.CorrelationScopeConfig;
import brave.baggage.CorrelationScopeCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BusinessProcessBaggageConfiguration {

//    BaggageField BUSINESS_PROCESS = BaggageField.create("bp");
//
//    /**
//     * {@link BaggageField#updateValue(TraceContext, String)} now flushes to MDC
//     */
//    @Bean
//    CorrelationScopeCustomizer flushBusinessProcessToMDCOnUpdate() {
//        return b -> b.add(CorrelationScopeConfig.SingleCorrelationField.newBuilder(BUSINESS_PROCESS).flushOnUpdate().build());
//    }


}
