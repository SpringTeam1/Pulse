package com.test.pulse.service.user;

/**
 * 이메일 발송 관련 서비스 인터페이스
 */
public interface MailService {

    /**
     * 지정된 이메일 주소로 인증 코드를 발송한다.
     * @param toEmail 수신자 이메일 주소
     * @param code 발송할 인증 코드
     */
    void sendVerificationCode(String toEmail, String code);
}
