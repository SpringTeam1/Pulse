package com.test.pulse.restcontroller.boardsuggestion;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boardsuggestion")
public class BoardSuggestionRestController {

	private final BoardSuggestionMapper mapper;
	
	@GetMapping("/list")
    public Map<String, Object> getBoardList(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String keyword,
        @RequestParam(defaultValue = "regdate") String sort,
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
	
	
	/** 좋아요 증가 */
    @PostMapping("/like/{boardContentSeq}")
    public Map<String, Object> like(@PathVariable String boardContentSeq) {

        mapper.updateLike(boardContentSeq);
        int newCount = mapper.getFavoriteCount(boardContentSeq);

        Map<String, Object> result = new HashMap<>();
        result.put("favoriteCount", newCount);
        return result;
    }

    /** 삭제 */
    @DeleteMapping("/delete/{boardContentSeq}")
    public Map<String, Object> del(@PathVariable String boardContentSeq) {

        mapper.delSuggestion(boardContentSeq);

        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        return result;
    }
	
	
    /** ✏ 게시글 수정 (REST + 파일 업로드) */
    @PostMapping("/edit/{boardContentSeq}")
    public ResponseEntity<Map<String, Object>> editSuggestion(
            @PathVariable("boardContentSeq") String boardContentSeq,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "attach", required = false) MultipartFile attach,
            HttpServletRequest req
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


