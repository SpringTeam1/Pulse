package com.test.pulse.service.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    @Resource(name = "mailSender")
    private JavaMailSender mailSender;

    @Value("${mail.username}")
    private String from;

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
                    + "<p>5분간 유효합니다.</p></div>";

            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("메일 전송 실패", e);
        }
    }
}
