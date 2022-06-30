package br.com.tokunaga.controller;

import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private final RestTemplate restTemplate;
    private final Tracer tracer;

    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping("/create")
    public ResponseEntity create() {
        Span span = tracer.buildSpan("create employee").start();

        span.setTag("http.status_code", 201);

        span.finish();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/path1")
    public ResponseEntity path1() {
        logger.info("Incoming request at {} for request /path1 ", applicationName);
        String response = restTemplate.getForObject("http://localhost:8090/service/path2", String.class);
        return ResponseEntity.ok("response from /path1 + " + response);
    }

    @GetMapping("/path2")
    public ResponseEntity path2() {
        logger.info("Incoming request at {} at /path2", applicationName);
        return ResponseEntity.ok("response from /path2 ");
    }
}