package com.test.pulse.restcontroller.boardsuggestion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.pulse.mapper.BoardSuggestionMapper;
import com.test.pulse.model.boardsuggestion.BoardSuggestionDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardSuggestionRestController {

	private final BoardSuggestionMapper mapper;
	
	@GetMapping("/boardsuggestion/api/list")
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
}
