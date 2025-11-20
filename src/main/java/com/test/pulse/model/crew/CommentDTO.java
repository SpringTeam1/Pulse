package com.test.pulse.model.crew;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "크루 전용 게시판 댓글 정보")
@Data
public class CommentDTO {

    @ApiModelProperty(value = "댓글 고유 번호", hidden = true)
    private String commentSeq;

    @ApiModelProperty(value = "크루 번호", hidden = true)
    private String crewSeq;

    @ApiModelProperty(value = "게시글 번호", required = true, example = "100")
    private String boardContentSeq;

    @ApiModelProperty(value = "작성자 ID", hidden = true)
    private String accountId;

    @ApiModelProperty(value = "댓글 내용", required = true, example = "좋은 정보 감사합니다.")
    private String content;

    @ApiModelProperty(value = "작성일", hidden = true)
    private String regdate;

    @ApiModelProperty(value = "작성자 닉네임", hidden = true)
    private String nickname;

}