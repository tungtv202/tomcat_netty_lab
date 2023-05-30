package com.example.reactive2;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class Reactive2Application {

    public static void main(String[] args) {
        SpringApplication.run(Reactive2Application.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {

        return RouterFunctions
            .route(GET("/netty").and(accept(MediaType.APPLICATION_JSON)), greetingHandler::hello);
    }

    @Component
    public class GreetingHandler {
        public Mono<ServerResponse> hello(ServerRequest request) {

            return Mono.delay(Duration.ofSeconds(5))
                .flatMap(e -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue("Hello, Netty. " + request.queryParam("name").orElse(""))));

        }
    }
}
