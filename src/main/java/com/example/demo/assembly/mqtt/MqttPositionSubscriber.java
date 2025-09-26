package com.example.demo.assembly.mqtt;

import com.example.demo.assembly.config.MqttProperties;
import com.example.demo.assembly.dto.PositionDto;
import com.example.demo.assembly.service.PositionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Component;

@Component
public class MqttPositionSubscriber implements MqttCallback {

    private final MqttClient client;
    private final MqttProperties props;
    private final PositionService positionService;
    private final ObjectMapper mapper = new ObjectMapper();

    public MqttPositionSubscriber(MqttClient client, MqttProperties props, PositionService positionService) {
        this.client = client;
        this.props = props;
        this.positionService = positionService;
    }

    @PostConstruct
    public void start() {
        // MQTT 연결 재시도를 별도의 스레드에서 실행
        Thread mqttThread = new Thread(() -> {
            try {
                connectAndSubscribe();
            } catch (Exception e) {
                System.err.println("[MQTT] 초기 연결 실패: " + e.getMessage());
            }
        }, "mqtt-subscriber-thread");
        mqttThread.setDaemon(true); // 백그라운드 스레드
        mqttThread.start();
    }

    private void connectAndSubscribe() {
        while (true) {
            try {
                MqttConnectOptions opts = new MqttConnectOptions();
                opts.setAutomaticReconnect(false); // 수동 재시도
                opts.setCleanSession(true);
                if (props.username() != null && !props.username().isBlank()) {
                    opts.setUserName(props.username());
                }
                if (props.password() != null && !props.password().isBlank()) {
                    opts.setPassword(props.password().toCharArray());
                }

                client.setCallback(this);
                client.connect(opts);

                // 콤마로 여러 토픽 가능
                for (String t : props.topic().split(",")) {
                    String topic = t.trim();
                    if (!topic.isEmpty()) {
                        client.subscribe(topic, props.qos());
                        System.out.println("[MQTT] Subscribed: " + topic);
                    }
                }
                System.out.println("[MQTT] Connected to " + props.brokerUrl());
                break;
            } catch (Exception e) {
                System.err.println("[MQTT] Connect failed: " + e.getMessage());
                try { Thread.sleep(props.reconnectDelayMs()); } catch (InterruptedException ignored) {}
            }
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.err.println("[MQTT] Connection lost: " + (cause != null ? cause.getMessage() : "unknown"));
        connectAndSubscribe();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        try {
            PositionDto dto = mapper.readValue(message.getPayload(), PositionDto.class);
            positionService.update(dto);
        } catch (Exception e) {
            System.err.println("[MQTT] Parse failed: " + e.getMessage());
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // subscriber only
    }
}
