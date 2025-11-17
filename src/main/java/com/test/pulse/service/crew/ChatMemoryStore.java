package com.test.pulse.service.crew;


import com.test.pulse.model.crew.ChatMessageDTO;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class ChatMemoryStore {

    private final Map<String, Deque<ChatMessageDTO>> roomMessages = new ConcurrentHashMap<>();

    public void addMessage(String crewSeq, ChatMessageDTO message) {
        roomMessages.putIfAbsent(crewSeq, new ConcurrentLinkedDeque<>());
        Deque<ChatMessageDTO> messages = roomMessages.get(crewSeq);

        messages.addLast(message);

        if(messages.size() > 50) {
            messages.removeFirst();
        }
    }

    public List<ChatMessageDTO> getRecentMessages(String crewSeq) {
        Deque<ChatMessageDTO> messages = roomMessages.get(crewSeq);

        if(messages == null) {
            return Collections.emptyList();
        }

        return new ArrayList<>(messages);
    }
}
