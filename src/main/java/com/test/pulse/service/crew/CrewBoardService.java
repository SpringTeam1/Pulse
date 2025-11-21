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

/**
 * 크루 게시판 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class CrewBoardService {

    private final CrewBoardMapper mapper;

    /**
     * 특정 크루의 게시글 목록을 페이징하여 조회한다.
     * @param map 페이징 정보 (begin, end)
     * @param crewSeq 크루 번호
     * @return 게시글 목록
     */
    public List<BoardDTO> list(HashMap<String, String> map, String crewSeq) {
        return mapper.list(map, crewSeq);
    }

    /**
     * 특정 게시글을 조회하고 조회수를 1 증가시킨다.
     * @param boardContentSeq 게시글 번호
     * @return 게시글 정보
     */
    public BoardDTO get(String boardContentSeq) {
        mapper.updateReadCount(boardContentSeq);
        return mapper.get(boardContentSeq);
    }

    /**
     * 새로운 게시글을 추가한다.
     * @param dto 추가할 게시글 정보
     * @return 추가된 게시글의 수
     */
    public int add(BoardDTO dto) {
        return mapper.add(dto);
    }

    /**
     * 기존 게시글을 수정한다.
     * @param dto 수정할 게시글 정보
     * @return 수정된 게시글의 수
     */
    public int update(BoardDTO dto) {
        return mapper.update(dto);
    }

    /**
     * 게시글을 삭제한다.
     * @param boardContentSeq 삭제할 게시글 번호
     * @return 삭제된 게시글의 수
     */
    public int remove(String boardContentSeq) {
        return mapper.remove(boardContentSeq);
    }

    /**
     * 게시글의 좋아요 수를 1 증가시키고 새로운 좋아요 수를 반환한다.
     * @param boardContentSeq 게시글 번호
     * @return 새로운 좋아요 수
     */
    public int updateLike(String boardContentSeq) {
        mapper.updateLike(boardContentSeq);
        return mapper.getFavoriteCount(boardContentSeq);
    }

    /**
     * 특정 크루에서 좋아요가 가장 많은 게시글을 조회한다.
     * @param crewSeq 크루 번호
     * @return 좋아요가 가장 많은 게시글 정보
     */
    public BoardDTO getLikeTop1BoardContent(String crewSeq) {
        return mapper.getLikeTop1BoardContent(crewSeq);
    }

    /**
     * 특정 크루의 총 게시글 수를 조회한다.
     * @param crewSeq 크루 번호
     * @return 총 게시글 수
     */
    public int getTotalCount(String crewSeq) {
        return mapper.getTotalCount(crewSeq);
    }

    /**
     * 특정 크루의 게시판에 있는 모든 사진을 조회한다.
     * @param crewSeq 크루 번호
     * @return 사진이 포함된 게시글 목록
     */
    public List<BoardDTO> getBoardPhotosByCrewSeq(String crewSeq) {
        return mapper.getBoardPhotosByCrewSeq(crewSeq);
    }

    /**
     * 첨부 파일을 저장하고 저장된 파일명을 반환한다.
     * @param file 첨부 파일
     * @param realPath 저장될 실제 경로
     * @return 저장된 파일명
     */
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

    /**
     * 이번 주의 인기 게시글 2개를 조회한다.
     * @param crewSeq 크루 번호
     * @return 주간 인기 게시글 정보
     */
    public BoardDTO getWeeklyTop2Posts(String crewSeq) {
        return mapper.getTotalCount2Week(crewSeq);
    }

    /**
     * 지난 한 주간의 총 게시글 수를 조회한다.
     * @param crewSeq 크루 번호
     * @return 주간 총 게시글 수
     */
    public int getTotalCount2Week(String crewSeq) {
        return mapper.getTotalPostCountWeek(crewSeq);
    }
}
