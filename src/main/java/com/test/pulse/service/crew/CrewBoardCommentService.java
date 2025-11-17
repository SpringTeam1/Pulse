package com.test.pulse.service.crew;

import com.test.pulse.mapper.CrewBoardCommentMapper;
import com.test.pulse.model.crew.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CrewBoardCommentService {

    private final CrewBoardCommentMapper mapper;

    public int add(CommentDTO dto) {
        return mapper.add(dto);
    }

    public List<CommentDTO> getCommentBoardContentSeq(String boardContentSeq) {
        return mapper.list(boardContentSeq);
    }


}
