package com.example.reactive1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = {"com.example.reactive1"})
@RestController
public class Reactive1Application {

    public static void main(String[] args) {
        SpringApplication.run(Reactive1Application.class, args);
    }

    @GetMapping("/tomcat")
    public ResponseEntity<?> hello(@RequestParam(required = false) String name) throws InterruptedException {
        Thread.sleep(5000);
        return ResponseEntity.ok("Hello, Tomcat. " + name);
    }
}
