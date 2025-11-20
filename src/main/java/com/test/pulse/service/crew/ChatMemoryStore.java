package com.test.pulse.service.crew;


import com.test.pulse.model.crew.ChatMessageDTO;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 채팅 메시지를 메모리에 저장하는 클래스
 */
@Component
public class ChatMemoryStore {

    private final Map<String, Deque<ChatMessageDTO>> roomMessages = new ConcurrentHashMap<>();

    /**
     * 채팅방에 메시지를 추가한다.
     * @param crewSeq 크루(채팅방) 번호
     * @param message 추가할 메시지
     */
    public void addMessage(String crewSeq, ChatMessageDTO message) {
        roomMessages.putIfAbsent(crewSeq, new ConcurrentLinkedDeque<>());
        Deque<ChatMessageDTO> messages = roomMessages.get(crewSeq);

        messages.addLast(message);

        if(messages.size() > 50) {
            messages.removeFirst();
        }
    }

    /**
     * 채팅방의 최근 메시지 내역을 조회한다.
     * @param crewSeq 크루(채팅방) 번호
     * @return 최근 메시지 목록
     */
    public List<ChatMessageDTO> getRecentMessages(String crewSeq) {
        Deque<ChatMessageDTO> messages = roomMessages.get(crewSeq);

        if(messages == null) {
            return Collections.emptyList();
        }

        return new ArrayList<>(messages);
    }
}
