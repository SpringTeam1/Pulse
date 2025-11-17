package com.test.pulse.mapper;

import com.test.pulse.model.crew.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CrewBoardCommentMapper {

    int add(CommentDTO dto);
    List<CommentDTO> list(String boardContentSeq);
}
