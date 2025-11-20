package com.test.pulse.model.crew;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 크루 전용 게시판의 게시글에 달린 댓글 정보를 담는 데이터 전송 객체(DTO)
 */
@ApiModel(description = "크루 전용 게시판 댓글 정보")
@Data
public class CommentDTO {

    /**
     * 댓글의 고유 번호 (Primary Key)
     */
    @ApiModelProperty(value = "댓글 고유 번호", hidden = true)
    private String commentSeq;

    /**
     * 댓글이 속한 크루의 고유 번호
     */
    @ApiModelProperty(value = "크루 번호", hidden = true)
    private String crewSeq;

    /**
     * 댓글이 달린 게시글의 고유 번호
     */
    @ApiModelProperty(value = "게시글 번호", required = true, example = "100")
    private String boardContentSeq;

    /**
     * 댓글을 작성한 사용자의 계정 ID
     */
    @ApiModelProperty(value = "작성자 ID", hidden = true)
    private String accountId;

    /**
     * 댓글의 내용
     */
    @ApiModelProperty(value = "댓글 내용", required = true, example = "좋은 정보 감사합니다.")
    private String content;

    /**
     * 댓글 작성일
     */
    @ApiModelProperty(value = "작성일", hidden = true)
    private String regdate;

    /**
     * 댓글을 작성한 사용자의 닉네임
     */
    @ApiModelProperty(value = "작성자 닉네임", hidden = true)
    private String nickname;

}