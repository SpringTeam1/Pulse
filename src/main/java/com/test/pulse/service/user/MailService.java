package com.test.pulse.service.user;

public interface MailService {
    void sendVerificationCode(String toEmail, String code);
}
