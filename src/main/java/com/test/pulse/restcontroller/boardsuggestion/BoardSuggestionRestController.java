package com.test.pulse.restcontroller.boardsuggestion;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.pulse.mapper.BoardSuggestionMapper;
import com.test.pulse.model.boardsuggestion.BoardSuggestionDTO;

import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 건의 게시판 관련 RESTful API를 제공하는 컨트롤러
 */
@Api(tags = "Board Suggestion API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boardsuggestion")
public class BoardSuggestionRestController {

	private final BoardSuggestionMapper mapper;

    /**
     * 페이징, 검색, 정렬 기능을 포함한 건의 게시판 목록을 조회한다.
     * @param page 페이지 번호
     * @param size 페이지 당 게시글 수
     * @param keyword 검색 키워드
     * @param sort 정렬 기준
     * @param order 정렬 순서
     * @return 게시글 목록, 전체 게시글 수, 전체 페이지 수 정보를 담은 Map 객체
     */
    @ApiOperation(value = "건의 게시판 목록 조회", notes = "페이징, 검색, 정렬 기능을 포함한 건의 게시판 목록을 조회한다.")
	@GetMapping("/list")
    public Map<String, Object> getBoardList(
            @ApiParam(value = "페이지 번호", defaultValue = "1", example = "1")
            @RequestParam(defaultValue = "1") int page,

            @ApiParam(value = "페이지 당 게시글 수", defaultValue = "10", example = "10")
            @RequestParam(defaultValue = "10") int size,

            @ApiParam(value = "검색 키워드")
            @RequestParam(required = false) String keyword,

            @ApiParam(value = "정렬 기준 (regdate: 등록일, ...)", defaultValue = "regdate")
            @RequestParam(defaultValue = "regdate") String sort,

            @ApiParam(value = "정렬 순서 (asc: 오름차순, desc: 내림차순)", defaultValue = "desc")
            @RequestParam(defaultValue = "desc") String order
    ) {
		int start = (page - 1) * size + 1;
	    int end = start + size - 1;

	    Map<String, Object> param = new HashMap<>();
	    param.put("start", start);
	    param.put("end", end);
	    param.put("keyword", keyword);
	    param.put("sort", sort);
	    param.put("order", order);

	    List<BoardSuggestionDTO> list = mapper.searchAndSort(param);
	    int totalCount = mapper.getTotalCount(keyword);

	    Map<String, Object> result = new HashMap<>();
	    result.put("list", list);
	    result.put("totalCount", totalCount);
	    result.put("totalPages", (int)Math.ceil(totalCount / (double)size));

        return result;
    }
	
	
	/**
     * 특정 게시글의 좋아요 수를 1 증가시킨다.
     * @param boardContentSeq 게시글 고유 번호
     * @return 새로운 좋아요 수를 담은 Map 객체
     */
    @ApiOperation(value = "게시글 좋아요", notes = "특정 게시글의 좋아요 수를 1개 증가시킨다.")
    @PostMapping("/like/{boardContentSeq}")
    public Map<String, Object> like(
            @ApiParam(value = "게시글 고유 번호(Seq)", required = true)
            @PathVariable String boardContentSeq) {

        mapper.updateLike(boardContentSeq);
        int newCount = mapper.getFavoriteCount(boardContentSeq);

        Map<String, Object> result = new HashMap<>();
        result.put("favoriteCount", newCount);
        return result;
    }

    /**
     * 특정 건의 게시글을 삭제한다.
     * @param boardContentSeq 삭제할 게시글 고유 번호
     * @return 삭제 성공 여부를 담은 Map 객체
     */
    @ApiOperation(value = "게시글 삭제", notes = "특정 건의 게시글을 삭제한다.")
    @DeleteMapping("/delete/{boardContentSeq}")
    public Map<String, Object> del(
            @ApiParam(value = "삭제할 게시글 고유 번호(Seq)", required = true)
            @PathVariable String boardContentSeq) {

        mapper.delSuggestion(boardContentSeq);

        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        return result;
    }
	
	
    /**
     * 게시글의 제목, 내용, 첨부파일을 수정한다.
     * @param boardContentSeq 수정할 게시글 고유 번호
     * @param title 수정할 제목
     * @param content 수정할 내용
     * @param attach 수정할 첨부파일
     * @param req HttpServletRequest 객체
     * @return 수정 결과와 리다이렉트 URL을 담은 ResponseEntity 객체
     */
    @ApiOperation(value = "게시글 수정", notes = "제목, 내용, 첨부파일을 수정한다. 파일 미첨부 시 기존 파일이 유지된다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "수정 성공"),
            @ApiResponse(code = 404, message = "게시글을 찾을 수 없음"),
            @ApiResponse(code = 500, message = "서버 오류 (파일 처리 실패 등)")
    })
    @PostMapping("/edit/{boardContentSeq}")
    public ResponseEntity<Map<String, Object>> editSuggestion(
            @ApiParam(value = "수정할 게시글 고유 번호(Seq)", required = true)
            @PathVariable("boardContentSeq") String boardContentSeq,

            @ApiParam(value = "수정할 제목", required = true)
            @RequestParam("title") String title,

            @ApiParam(value = "수정할 내용", required = true)
            @RequestParam("content") String content,

            @ApiParam(value = "수정할 첨부파일 (없으면 기존 유지)")
            @RequestParam(value = "attach", required = false) MultipartFile attach,

            @ApiIgnore HttpServletRequest req
    ) {

        Map<String, Object> result = new HashMap<>();

        try {
            // 1) 기존 DTO 조회
            BoardSuggestionDTO dto = mapper.getSuggestion(boardContentSeq);

            if (dto == null) {
                result.put("status", "NOT_FOUND");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }

            // 2) 업로드 경로
            String uploadPath = req.getServletContext().getRealPath("/boardsuggestion");
            File dir = new File(uploadPath);
            if (!dir.exists()) dir.mkdirs();

            // 기본값 = 기존 첨부 유지
            String newFileName = dto.getAttach();

            // 3) 새 파일 업로드가 있을 경우
            if (attach != null && !attach.isEmpty()) {

                // (1) 기존 파일 삭제
                if (dto.getAttach() != null && !dto.getAttach().equals("void")) {
                    File oldFile = new File(dir, dto.getAttach());
                    if (oldFile.exists()) oldFile.delete();
                }

                // (2) 새 파일 저장
                newFileName = System.currentTimeMillis() + "_" + attach.getOriginalFilename();
                File newFile = new File(dir, newFileName);
                attach.transferTo(newFile);
            }

            // 4) 수정 데이터 세팅
            dto.setTitle(title);
            dto.setContent(content);
            dto.setAttach(newFileName);

            int cnt = mapper.editSuggestion(dto);

            if (cnt == 1) {
                result.put("status", "OK");
                // 클라이언트에서 ctx 붙여서 사용
                result.put("redirectUrl", "/boardsuggestion/view?boardContentSeq=" + boardContentSeq);
                return ResponseEntity.ok(result);
            } else {
                result.put("status", "FAIL");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "ERROR");
            result.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
	
	
	
}


