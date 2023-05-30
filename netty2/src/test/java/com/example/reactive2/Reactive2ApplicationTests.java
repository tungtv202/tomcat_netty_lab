package com.example.reactive2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;


@Slf4j(topic = "test")
class Reactive2ApplicationTests {
    @Test
    void stress() {
        Scheduler newBoundedElastic = Schedulers.newBoundedElastic(8, 20, "test-scheduler");
        var webClient = WebClient
            .builder()
            .baseUrl("http://localhost:8081")
            .build();

        Function<String, Mono<String>> clientPublisher = counter -> webClient
            .get().uri("/netty?name=" + counter)
            .retrieve()
            .bodyToMono(String.class)
            .doOnNext(e -> log.info("Response: {}", e));

        long start = System.currentTimeMillis();

        Flux.range(0, 1000)
            .flatMap(i -> clientPublisher.apply(String.valueOf(i)), 1000)
            .subscribeOn(newBoundedElastic)
            .last()
            .block();

        System.out.println("Spent time: " + TimeUnit.SECONDS
            .convert(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS));
    }

}
