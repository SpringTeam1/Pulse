package com.test.pulse.model.crew;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "크루 채팅 메시지 정보")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    @ApiModelProperty(value = "보낸 사람 ID", hidden = true)
    private String senderId;

    @ApiModelProperty(value = "보낸 사람 닉네임", hidden = true)
    private String nickname;

    @ApiModelProperty(value = "보낸 사람 프로필 이미지", hidden = true)
    private String profilePic;

    @ApiModelProperty(value = "메시지 내용", required = true, example = "안녕하세요.")
    private String message;

    @ApiModelProperty(value = "전송 시간", hidden = true)
    private String timestamp;

    @ApiModelProperty(value = "크루 번호", example = "1", required = true)
    private String crewSeq;
}
