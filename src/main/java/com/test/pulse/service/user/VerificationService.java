package com.test.pulse.service.user;

public interface VerificationService {
    String issueCode(String email);             // 새 코드 발급(재발급 시 덮어씀)
    boolean verify(String email, String code);  // 코드 검증(성공 시 해당 이메일 '인증됨' 상태로 기록)
    boolean isVerified(String email);           // 현재 인증 완료 상태인지
    void clear(String email);                   // 가입 완료 등으로 정리 시
}
