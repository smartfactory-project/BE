package com.example.demo.process.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProcessControlConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SimpMessagingTemplate messagingTemplate;

    public ProcessControlConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "process-control", groupId = "station-monitor")
    public void consume(String message) {
        try {
            Map<String, Object> data = objectMapper.readValue(message, Map.class);
            String stationId = (String) data.get("station_id");
            if (stationId != null) {
                // 프론트에서 구독할 수 있도록 stationId별 토픽에 브로드캐스트
                messagingTemplate.convertAndSend("/topic/station/" + stationId, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
