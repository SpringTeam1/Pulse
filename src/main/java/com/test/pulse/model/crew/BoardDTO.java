package com.test.pulse.model.crew;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "크루 전용 게시판 게시글 정보")
@Data
public class BoardDTO {
    @ApiModelProperty(value = "게시글 번호", hidden = true)
    private int boardContentSeq;

    @ApiModelProperty(value = "크루 번호", example = "1", required = true)
    private int crewSeq;

    @ApiModelProperty(value = "작성자 ID", hidden = true)
    private String accountId;

    @ApiModelProperty(value = "게시글 제목", required = true, example = "이번 주 모임 공지")
    private String title;

    @ApiModelProperty(value = "게시글 내용", required = true, example = "이번 주는 비가 와서 취소합니다.")
    private String content;

    @ApiModelProperty(value = "작성자 ID", hidden = true)
    private String attach;

    @ApiModelProperty(value = "등록 날짜", hidden = true) // DB 자동 생성 (sysdate)
    private String regdate;

    @ApiModelProperty(value = "조회수", hidden = true) // 초기값 0, 조회 시 증가
    private int readCount;

    @ApiModelProperty(value = "좋아요 수", hidden = true) // 초기값 0, 좋아요 클릭 시 증가
    private int favoriteCount;

    @ApiModelProperty(value = "게시판 타입 번호", hidden = true) // 로직 내부에서 고정값으로 처리
    private int boardContentTypeSeq;

    @ApiModelProperty(value = "작성자 닉네임", hidden = true)
    private String nickname;

}
