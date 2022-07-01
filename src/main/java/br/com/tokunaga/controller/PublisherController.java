package br.com.tokunaga.controller;

import br.com.tokunaga.sqs.SQSEventPublisher;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PublisherController {
    private final SQSEventPublisher sqsEventPublisher;

    @Value("${aws.sqs.queue}")
    private String queue;

    @PostMapping("/sqs")
    public ResponseEntity sendMessage(@RequestBody JsonNode json) {
        sqsEventPublisher.publish(queue, json);
        return ResponseEntity.ok().build();
    }
}