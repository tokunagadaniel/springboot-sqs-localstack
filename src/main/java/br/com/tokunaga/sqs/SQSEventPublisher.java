package br.com.tokunaga.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SQSEventPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(SQSEventPublisher.class);
    private final AmazonSQS amazonSQS;
    private final ObjectMapper objectMapper;

    public void publish(String endpoint, JsonNode message) {
        try {
            SendMessageRequest sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl(endpoint)
                    .withMessageBody(objectMapper.writeValueAsString(message));

            amazonSQS.sendMessage(sendMessageRequest);

            LOGGER.info("Event has been published in SQS.");
        } catch (JsonProcessingException e) {
            LOGGER.error("JsonProcessingException e : {} and stacktrace : {}", e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Exception ocurred while pushing event to sqs : {} and stacktrace ; {}", e.getMessage(), e);
        }
    }
}