package com.mynotes.spring.tracing.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.sleuth.CurrentTraceContext;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class TraceIdResponse implements GlobalFilter {

    final Tracer tracer;

    final CurrentTraceContext currentTraceContext;

    public TraceIdResponse(Tracer tracer, CurrentTraceContext currentTraceContext) {
        this.tracer = tracer;
        this.currentTraceContext = currentTraceContext;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Span span = tracer.currentSpan();
        if (span != null) {
            String traceId = span.context().traceId();
            exchange.getResponse().getHeaders().add("Trace-Id", traceId);
        }

        return chain.filter(exchange);
    }
}
