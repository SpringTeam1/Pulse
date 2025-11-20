package com.test.pulse.service.user;

/**
 * 사용자 인증 관련 서비스 인터페이스
 */
public interface VerificationService {

    /**
     * 새로운 인증 코드를 발급한다. (재발급 시 덮어씀)
     * @param email 이메일 주소
     * @return 발급된 인증 코드
     */
    String issueCode(String email);

    /**
     * 코드를 검증하고, 성공 시 해당 이메일을 '인증됨' 상태로 기록한다.
     * @param email 이메일 주소
     * @param code 인증 코드
     * @return 검증 성공 여부
     */
    boolean verify(String email, String code);

    /**
     * 현재 인증 완료 상태인지 확인한다.
     * @param email 이메일 주소
     * @return 인증 완료 여부
     */
    boolean isVerified(String email);

    /**
     * 가입 완료 등으로 인증 상태를 정리한다.
     * @param email 이메일 주소
     */
    void clear(String email);
}
