package com.test.pulse.service.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

/**
 * MailService 인터페이스의 구현 클래스
 */
@Service
public class MailServiceImpl implements MailService {

    @Resource(name = "mailSender")
    private JavaMailSender mailSender;

    @Value("${mail.username}")
    private String from;

    /**
     * 지정된 이메일 주소로 인증 코드를 발송한다.
     * @param toEmail 수신자 이메일 주소
     * @param code 발송할 인증 코드
     */
    @Override
    public void sendVerificationCode(String toEmail, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setFrom(from, "Pulse");
            helper.setTo(toEmail);
            helper.setSubject("[Pulse] 이메일 인증번호");

            String html = "<div style='font-size:14px; font-family:sans-serif;'>"
                    + "<p>인증번호:</p>"
                    + "<p style='font-size:24px; font-weight:bold;'>" + code + "</p>"
                    + "<p>5분간 유효한다.</p></div>";

            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("메일 전송 실패", e);
        }
    }
}
