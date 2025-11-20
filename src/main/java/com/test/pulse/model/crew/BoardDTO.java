package com.test.pulse.model.crew;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 크루 전용 게시판의 단일 게시글 정보를 담는 데이터 전송 객체(DTO)
 */
@ApiModel(description = "크루 전용 게시판 게시글 정보")
@Data
public class BoardDTO {
    /**
     * 게시글의 고유 번호 (Primary Key)
     */
    @ApiModelProperty(value = "게시글 번호", hidden = true)
    private int boardContentSeq;

    /**
     * 게시글이 속한 크루의 고유 번호
     */
    @ApiModelProperty(value = "크루 번호", example = "1", required = true)
    private int crewSeq;

    /**
     * 게시글을 작성한 사용자의 계정 ID
     */
    @ApiModelProperty(value = "작성자 ID", hidden = true)
    private String accountId;

    /**
     * 게시글의 제목
     */
    @ApiModelProperty(value = "게시글 제목", required = true, example = "이번 주 모임 공지")
    private String title;

    /**
     * 게시글의 내용
     */
    @ApiModelProperty(value = "게시글 내용", required = true, example = "이번 주는 비가 와서 취소합니다.")
    private String content;

    /**
     * 첨부 파일의 파일명
     */
    @ApiModelProperty(value = "첨부 파일명", hidden = true)
    private String attach;

    /**
     * 게시글 등록일
     */
    @ApiModelProperty(value = "등록 날짜", hidden = true) // DB 자동 생성 (sysdate)
    private String regdate;

    /**
     * 게시글 조회수
     */
    @ApiModelProperty(value = "조회수", hidden = true) // 초기값 0, 조회 시 증가
    private int readCount;

    /**
     * 게시글 '좋아요' 수
     */
    @ApiModelProperty(value = "좋아요 수", hidden = true) // 초기값 0, 좋아요 클릭 시 증가
    private int favoriteCount;

    /**
     * 게시판의 종류를 구분하는 번호
     */
    @ApiModelProperty(value = "게시판 타입 번호", hidden = true) // 로직 내부에서 고정값으로 처리
    private int boardContentTypeSeq;

    /**
     * 게시글을 작성한 사용자의 닉네임
     */
    @ApiModelProperty(value = "작성자 닉네임", hidden = true)
    private String nickname;

}
