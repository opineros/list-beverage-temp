package com.opineros.listbeverage.gateway.filter;

import com.opineros.listbeverage.gateway.service.AuditPublisher;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Map;

@Component
public class GatewayAuditFilter implements GlobalFilter {

    private final AuditPublisher auditPublisher;

    public GatewayAuditFilter(AuditPublisher auditPublisher) {
        this.auditPublisher = auditPublisher;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest req = exchange.getRequest();
        HttpMethod method = req.getMethod();
        String contentType = req.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);

        // Sólo intentamos leer body en métodos que lo llevan
        boolean methodHasBody = method == HttpMethod.POST || method == HttpMethod.PUT || method == HttpMethod.PATCH;

        if (!methodHasBody) {
            // Sin body → solo audita metadata
            auditPublisher.publish("REQUEST_RECEIVED", Map.of(
                    "path", req.getURI().getPath(),
                    "method", method != null ? method.name() : "UNKNOWN",
                    "timestamp", Instant.now().toString()
            ));
            return chain.filter(exchange);
        }

        // Unir el body (DataBuffer → String) y luego volver a colocarlo para que el downstream pueda leerlo
        return DataBufferUtils.join(req.getBody())
                .defaultIfEmpty(exchange.getResponse().bufferFactory().wrap(new byte[0]))
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer); // evitar fugas

                    String body = new String(bytes, StandardCharsets.UTF_8);

                    // --- Aquí haces tu auditoría con el body ---
                    auditPublisher.publish("REQUEST_WITH_BODY", Map.of(
                            "path", req.getURI().getPath(),
                            "method", method != null ? method.name() : "UNKNOWN",
                            "contentType", contentType != null ? contentType : "",
                            "body", body,
                            "timestamp", Instant.now().toString()
                    ));

                    // Volver a inyectar el body para el siguiente filtro/route
                    DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
                    byte[] newBytes = body.getBytes(StandardCharsets.UTF_8);

                    Flux<DataBuffer> cachedBody = Flux.defer(() ->
                            Mono.just(bufferFactory.wrap(newBytes))
                    );

                    HttpHeaders newHeaders = new HttpHeaders();
                    newHeaders.putAll(req.getHeaders());
                    newHeaders.remove(HttpHeaders.CONTENT_LENGTH);
                    newHeaders.setContentLength(newBytes.length);

                    ServerHttpRequest decorated = new ServerHttpRequestDecorator(req) {
                        @Override
                        public HttpHeaders getHeaders() {
                            return newHeaders;
                        }

                        @Override
                        public Flux<DataBuffer> getBody() {
                            return cachedBody;
                        }
                    };

                    return chain.filter(exchange.mutate().request(decorated).build());
                });
    }
}