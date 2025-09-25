package com.example.demo.assembly.service;

import com.example.demo.assembly.dto.PositionDto;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PositionService {

    private final AtomicReference<PositionDto> latest = new AtomicReference<>();
    private final Set<SseEmitter> emitters = ConcurrentHashMap.newKeySet();

    public void update(PositionDto dto) {
        latest.set(dto);

        emitters.removeIf(emitter -> {
            try {
                emitter.send(SseEmitter.event().name("position").data(dto));
                return false;
            } catch (Exception e) {
                emitter.completeWithError(e);
                return true;
            }
        });
    }

    public PositionDto latest() {
        return latest.get();
    }

    public SseEmitter registerEmitter(long timeoutMs) {
        SseEmitter emitter = new SseEmitter(timeoutMs);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitters.add(emitter);

        PositionDto current = latest.get();
        if (current != null) {
            try { emitter.send(SseEmitter.event().name("position").data(current)); }
            catch (Exception ignored) {}
        }
        return emitter;
    }
}
