package com.test.pulse.model.crew;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    private String senderId;
    private String nickname;
    private String profilePic;
    private String message;
    private String timestamp;
    private String crewSeq;
}
