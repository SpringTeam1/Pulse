package com.test.pulse.model.course;

import lombok.Getter;
import lombok.ToString;

/**
 * 목록 페이지의 페이징(페이지 번호 매기기) 계산에 필요한 모든 정보를 담고 처리하는 데이터 전송 객체(DTO)
 * 총 항목 수와 현재 페이지 번호를 기반으로 나머지 모든 값을 계산한다.
 */
@Getter
@ToString
public class PageDTO {
    /**
     * 현재 사용자가 보고 있는 페이지 번호
     */
    private int currentPage;

    /**
     * 페이징 대상이 되는 전체 항목(게시물, 코스 등)의 수
     */
    private int totalCount;

    /**
     * 한 페이지에 표시될 항목의 수
     */
    private int pageSize = 12;

    /**
     * 전체 페이지의 수
     */
    private int totalPage;

    /**
     * 현재 화면에 보이는 페이지 번호 블록의 시작 번호
     */
    private int startPage;

    /**
     * 현재 화면에 보이는 페이지 번호 블록의 끝 번호
     */
    private int endPage;

    /**
     * 한 화면에 표시할 페이지 번호의 개수 (예: [1][2]...[10])
     */
    private int blockSize = 10;

    /**
     * PageDTO의 생성자
     * 총 항목 수와 현재 페이지 번호를 받아 모든 페이징 관련 값을 계산하여 초기화한다.
     *
     * @param totalCount  전체 항목의 수
     * @param currentPage 사용자가 요청한 현재 페이지 번호
     */
    public PageDTO(int totalCount, int currentPage) {
        this.totalCount = totalCount;
        this.currentPage = currentPage;

        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
        this.startPage = ((currentPage - 1) / blockSize) * blockSize + 1;
        this.endPage = this.startPage + blockSize - 1;

        if (this.endPage > this.totalPage) {
            this.endPage = this.totalPage;
        }
    }
}
