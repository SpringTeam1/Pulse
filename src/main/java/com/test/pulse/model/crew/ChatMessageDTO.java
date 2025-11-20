package com.test.pulse.model.crew;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 크루의 실시간 채팅방에서 사용되는 단일 메시지 정보를 담는 데이터 전송 객체(DTO)
 * WebSocket을 통해 클라이언트와 서버 간에 메시지를 주고받을 때 사용
 */
@ApiModel(description = "크루 채팅 메시지 정보")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    /**
     * 메시지를 보낸 사람의 계정 ID
     */
    @ApiModelProperty(value = "보낸 사람 ID", hidden = true)
    private String senderId;

    /**
     * 메시지를 보낸 사람의 닉네임
     */
    @ApiModelProperty(value = "보낸 사람 닉네임", hidden = true)
    private String nickname;

    /**
     * 메시지를 보낸 사람의 프로필 사진 파일명
     */
    @ApiModelProperty(value = "보낸 사람 프로필 이미지", hidden = true)
    private String profilePic;

    /**
     * 메시지의 실제 내용
     */
    @ApiModelProperty(value = "메시지 내용", required = true, example = "안녕하세요.")
    private String message;

    /**
     * 메시지가 전송된 시간
     */
    @ApiModelProperty(value = "전송 시간", hidden = true)
    private String timestamp;

    /**
     * 메시지가 전송된 크루의 고유 번호
     */
    @ApiModelProperty(value = "크루 번호", example = "1", required = true)
    private String crewSeq;
}
