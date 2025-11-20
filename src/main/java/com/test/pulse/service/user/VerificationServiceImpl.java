package com.test.pulse.service.user;

import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * VerificationService 인터페이스의 구현 클래스
 */
@Service
public class VerificationServiceImpl implements VerificationService {

    private static final long EXPIRE_SEC = 5 * 60; // 5분
    private final SecureRandom random = new SecureRandom();

    /**
     * 인증 정보를 저장하는 내부 클래스
     */
    private static class Entry {
        String code;
        long issuedAt;   // epoch seconds
        boolean verified;
    }

    private final Map<String, Entry> store = new ConcurrentHashMap<>();

    /**
     * 새로운 인증 코드를 발급한다.
     * @param email 이메일 주소
     * @return 발급된 인증 코드
     */
    @Override
    public String issueCode(String email) {
        String code = generate6();
        Entry e = new Entry();
        e.code = code;
        e.issuedAt = Instant.now().getEpochSecond();
        e.verified = false;
        store.put(email, e);
        return code;
    }

    /**
     * 코드를 검증하고, 성공 시 해당 이메일을 '인증됨' 상태로 기록한다.
     * @param email 이메일 주소
     * @param code 인증 코드
     * @return 검증 성공 여부
     */
    @Override
    public boolean verify(String email, String code) {
        Entry e = store.get(email);
        if (e == null) return false;
        long now = Instant.now().getEpochSecond();
        if (now - e.issuedAt > EXPIRE_SEC) return false;
        if (!e.code.equals(code)) return false;
        e.verified = true;
        return true;
    }

    /**
     * 현재 인증 완료 상태인지 확인한다.
     * @param email 이메일 주소
     * @return 인증 완료 여부
     */
    @Override
    public boolean isVerified(String email) {
        Entry e = store.get(email);
        if (e == null) return false;
        long now = Instant.now().getEpochSecond();
        if (now - e.issuedAt > EXPIRE_SEC) return false;
        return e.verified;
    }

    /**
     * 인증 상태를 정리한다.
     * @param email 이메일 주소
     */
    @Override
    public void clear(String email) {
        store.remove(email);
    }

    /**
     * 6자리 난수를 생성한다.
     * @return 6자리 난수 문자열
     */
    private String generate6() {
        int n = random.nextInt(1_000_000); // 0~999999
        return String.format("%06d", n);
    }
}
