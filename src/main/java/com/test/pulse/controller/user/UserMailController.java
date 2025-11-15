package com.test.pulse.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.pulse.service.user.MailService;
import com.test.pulse.service.user.VerificationService;

@Controller
@RequestMapping("/user")
public class UserMailController {

    @Resource
    private VerificationService verificationService;
    @Resource
    private MailService mailService;

    // AJAX: 인증번호 발송
    @PostMapping(value="/sendmail.do")
    @ResponseBody
    public Map<String, Object> send(@RequestParam("email") String email) {
        Map<String, Object> res = new HashMap<>();
        try {
            String code = verificationService.issueCode(email);
            mailService.sendVerificationCode(email, code);
            res.put("result", 1);
        } catch (Exception e) {
            res.put("result", 0);
        }
        return res;
    }

    // AJAX: 코드 검증
    @PostMapping(value="/verifycode.do")
    @ResponseBody
    public Map<String, Object> verify(@RequestParam("email") String email,
                                      @RequestParam("code") String code) {
        Map<String, Object> res = new HashMap<>();
        boolean ok = verificationService.verify(email, code);
        res.put("ok", ok);
        return res;
    }
}
