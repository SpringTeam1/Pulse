package com.test.pulse.mapper;


import com.test.pulse.model.crew.BoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface CrewBoardMapper {

    List<BoardDTO> list(@Param("map") HashMap<String, String> map,
                        @Param("crewSeq") String crewSeq);

    BoardDTO get(@Param("boardContentSeq") String boardContentSeq);

    int add(BoardDTO dto);

    int update(BoardDTO dto);

    int remove(@Param("boardContentSeq") String boardContentSeq);

    int getTotalCount(@Param("crewSeq") String crewSeq);

    void updateReadCount(@Param("boardContentSeq") String boardContentSeq);

    void updateLike(@Param("boardContentSeq") String boardContentSeq);

    List<BoardDTO> getBoardPhotosByCrewSeq(@Param("crewSeq") String crewSeq);

    int getFavoriteCount (@Param("boardContentSeq") String boardContentSeq);

    int getTotalPostCount (@Param("crewSeq") String crewSeq);

    List<BoardDTO> getWeeklyTop2Posts(@Param("crewSeq") String crewSeq);

    int getTotalCount2Week(String crewSeq);
}
