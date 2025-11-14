package com.test.pulse.service.crew;

import com.test.pulse.mapper.CrewBoardMapper;
import com.test.pulse.model.crew.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrewBoardService {

    private final CrewBoardMapper mapper;

    public List<BoardDTO> list(HashMap<String, String> map, String crewSeq) {
        return mapper.list(map, crewSeq);
    }

    public BoardDTO get(String boardContentSeq) {
        mapper.updateReadCount(boardContentSeq);
        return mapper.get(boardContentSeq);
    }

    public int add(BoardDTO dto) {
        return mapper.add(dto);
    }

    public int update(BoardDTO dto) {
        return mapper.update(dto);
    }

    public int remove(String boardContentSeq) {
        return mapper.remove(boardContentSeq);
    }

    public int updateLike(String boardContentSeq) {
        mapper.updateLike(boardContentSeq);
        return mapper.getFavoriteCount(boardContentSeq);
    }

    public int getTotalCount(String crewSeq) {
        return mapper.getTotalCount(crewSeq);
    }

    public List<BoardDTO> getBoardPhotosByCrewSeq(String crewSeq) {
        return mapper.getBoardPhotosByCrewSeq(crewSeq);
    }

    public String saveAttach(MultipartFile file, String realPath) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            // 저장할 파일명 생성
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // ✅ OS에 맞게 안전하게 경로 결합 (REST API의 uploadPath와 호환)
            Path savePath = Paths.get(realPath, fileName);

            // 상위 디렉토리 없으면 생성
            Files.createDirectories(savePath.getParent());

            // 파일 저장 (기존 파일 덮어쓰기 방지용 옵션)
            Files.copy(file.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);

            return fileName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BoardDTO getWeeklyTop2Posts(String crewSeq) {
        return mapper.getTotalCount2Week(crewSeq);
    }

    public int getTotalCount2Week(String crewSeq) {
        return mapper.getTotalPostCountWeek(crewSeq);
    }
}
