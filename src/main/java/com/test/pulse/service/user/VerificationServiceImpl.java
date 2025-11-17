package com.test.pulse.service.user;

import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VerificationServiceImpl implements VerificationService {

    private static final long EXPIRE_SEC = 5 * 60; // 5분
    private final SecureRandom random = new SecureRandom();

    private static class Entry {
        String code;
        long issuedAt;   // epoch seconds
        boolean verified;
    }

    private final Map<String, Entry> store = new ConcurrentHashMap<>();

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

    @Override
    public boolean isVerified(String email) {
        Entry e = store.get(email);
        if (e == null) return false;
        long now = Instant.now().getEpochSecond();
        if (now - e.issuedAt > EXPIRE_SEC) return false;
        return e.verified;
    }

    @Override
    public void clear(String email) {
        store.remove(email);
    }

    private String generate6() {
        int n = random.nextInt(1_000_000); // 0~999999
        return String.format("%06d", n);
    }
}
