package com.test.pulse.restcontroller.crew;

import com.test.pulse.model.crew.ChatMessageDTO;
import com.test.pulse.service.crew.ChatMemoryStore;
import com.test.pulse.service.crew.SseEmitterStore;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 크루 채팅 관련 RESTful API를 제공하는 컨트롤러
 */
@Api(tags = "Crew Chat API")
@RestController
@RequestMapping("/api/v1/crew/chat")
@RequiredArgsConstructor
public class CrewChatRestController {
    private final ChatMemoryStore chatMemoryStore;
    private final SseEmitterStore sseEmitterStore;

    /**
     * Server-Sent Events를 통해 실시간 채팅을 위한 연결을 맺는다.
     * @param crewSeq 입장할 크루(채팅방) 번호
     * @return SseEmitter 객체
     */
    @ApiOperation(value = "채팅방 입장 (SSE 연결)", notes = "Server-Sent Events를 통해 실시간 채팅을 위한 연결을 맺는다.")
    @GetMapping(value="/stream/{crewSeq}", produces = "text/event-stream")
    public SseEmitter connect(
            @ApiParam(value = "입장할 크루(채팅방) 번호", required = true)
            @PathVariable String crewSeq) {

        SseEmitter emitter = sseEmitterStore.addEmitter(crewSeq);

        List<ChatMessageDTO> history = chatMemoryStore.getRecentMessages(crewSeq);
        try {
            emitter.send(SseEmitter.event().name("history").data(history));
        } catch (IOException e) {

        }
        return emitter;
    }

    /**
     * 채팅방에 메시지를 전송한다.
     * @param crewSeq 크루 번호
     * @param message 전송할 메시지 정보
     */
    @ApiOperation(value = "메시지 전송", notes = "채팅방에 메시지를 전송한다.")
    @PostMapping("/send/{crewSeq}")
    public void sendMessage(
            @ApiParam(value = "크루 번호", required = true)
            @PathVariable String crewSeq,
            @RequestBody ChatMessageDTO message) {
        message.setTimestamp(LocalDateTime.now().toString());
        message.setCrewSeq(crewSeq);
        chatMemoryStore.addMessage(crewSeq, message);

        sseEmitterStore.sendToRoom(crewSeq, message);
    }

    /**
     * 채팅방의 최근 메시지 내역을 조회한다.
     * @param crewSeq 크루 번호
     * @return 최근 메시지 목록
     */
    @ApiOperation(value = "최근 메시지 조회", notes = "채팅방의 최근 메시지 내역을 조회한다.")
    @GetMapping("/recent/{crewSeq}")
    public List<ChatMessageDTO> recent(
            @ApiParam(value = "크루 번호", required = true)
            @PathVariable String crewSeq) {
        return chatMemoryStore.getRecentMessages(crewSeq);
    }
}