package com.example.demo.assembly.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.mqtt")
public record MqttProperties(
        String brokerUrl,
        String clientId,
        String topic,        // 콤마로 여러 토픽 가능: "uwb/position,topic2"
        int qos,
        long reconnectDelayMs,
        String username,     // 선택
        String password      // 선택
) {}
