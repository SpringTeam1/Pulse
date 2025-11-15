package com.test.pulse.service.crew;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SseEmitterStore {

    private final Map<String, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

    public SseEmitter addEmitter(String crewSeq){
        emitters.putIfAbsent(crewSeq, Collections.synchronizedList(new ArrayList<>()));

        SseEmitter emitter = new SseEmitter(0L); // ★ timeout 없음

        emitters.get(crewSeq).add(emitter);

        // 연결 종료 시 정리
        emitter.onCompletion(() -> emitters.get(crewSeq).remove(emitter));
        emitter.onTimeout(() -> emitters.get(crewSeq).remove(emitter));
        emitter.onError((e) -> emitters.get(crewSeq).remove(emitter));

        // 연결 직후 더미 ping 전송 (중요)
        try {
            emitter.send(SseEmitter.event().name("ping").data("connected"));
        } catch (Exception ignored) {}

        return emitter;
    }

    public void sendToRoom(String crewSeq, Object message){
        List<SseEmitter> list = emitters.get(crewSeq);
        if(list == null) return;

        List<SseEmitter> dead = new ArrayList<>();

        for (SseEmitter emitter : list) {
            try{
                emitter.send(SseEmitter.event().name("chat").data(message));
            } catch(Exception e){
                dead.add(emitter);
            }
        }

        list.removeAll(dead);
    }
}
