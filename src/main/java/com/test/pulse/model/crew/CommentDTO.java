package com.test.pulse.model.crew;

import lombok.Data;

@Data
public class CommentDTO {

    private String commentSeq ;
    private String crewSeq;
    private String boardContentSeq;
    private String accountId;
    private String content;
    private String regdate;
    private String nickname;

}