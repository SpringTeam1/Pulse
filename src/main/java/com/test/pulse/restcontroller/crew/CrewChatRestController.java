package com.test.pulse.restcontroller.crew;

import com.test.pulse.model.crew.ChatMessageDTO;
import com.test.pulse.service.crew.ChatMemoryStore;
import com.test.pulse.service.crew.SseEmitterStore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/crew/chat")
@RequiredArgsConstructor
public class CrewChatRestController {
    private final ChatMemoryStore chatMemoryStore;
    private final SseEmitterStore sseEmitterStore;

    @GetMapping(value="/stream/{crewSeq}", produces = "text/event-stream")
    public SseEmitter connect(@PathVariable String crewSeq) {

        SseEmitter emitter = sseEmitterStore.addEmitter(crewSeq);

        List<ChatMessageDTO> history = chatMemoryStore.getRecentMessages(crewSeq);
        try {
            emitter.send(SseEmitter.event().name("history").data(history));
        } catch (IOException e) {

        }
        return emitter;
    }

    @PostMapping("/send/{crewSeq}")
    public void sendMessage(
            @PathVariable String crewSeq,
            @RequestBody ChatMessageDTO message) {
        message.setTimestamp(LocalDateTime.now().toString());
        message.setCrewSeq(crewSeq);
        chatMemoryStore.addMessage(crewSeq, message);

        sseEmitterStore.sendToRoom(crewSeq, message);
    }

    @GetMapping("/recent/{crewSeq}")
    public List<ChatMessageDTO> recent(@PathVariable String crewSeq) {
        return chatMemoryStore.getRecentMessages(crewSeq);
    }
}