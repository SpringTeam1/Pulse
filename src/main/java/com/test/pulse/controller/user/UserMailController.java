package com.test.pulse.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.pulse.mapper.UserMapper;
import com.test.pulse.model.user.AccountInfoDTO;
import com.test.pulse.service.user.MailService;
import com.test.pulse.service.user.VerificationService;

/**
 * 사용자 이메일 인증 관련 요청을 처리하는 컨트롤러 클래스
 */
@Controller
@RequestMapping("/user")
public class UserMailController {

    @Resource
    private VerificationService verificationService;
    @Resource
    private MailService mailService;
    @Autowired
    private UserMapper userMapper;

    /**
     * 이메일 중복 여부를 확인한다.
     *
     * @param email 확인할 이메일 주소
     * @return 중복 여부를 담은 Map 객체 ({"exists": true/false})
     */
    @PostMapping("/checkEmail")
    @ResponseBody
    public Map<String, Object> checkEmail(@RequestParam String email) {
        Map<String, Object> result = new HashMap<>();
        AccountInfoDTO dto = userMapper.get(email); // 또는 infoEditService.get(email)
        result.put("exists", dto != null);
        return result;
    }

    /**
     * 이메일로 인증번호를 발송한다. (AJAX)
     *
     * @param email 인증번호를 받을 이메일 주소
     * @return 발송 결과를 담은 Map 객체 ({"result": 1 or 0})
     */
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

    /**
     * 입력된 인증번호를 검증한다. (AJAX)
     *
     * @param email 이메일 주소
     * @param code  사용자가 입력한 인증번호
     * @return 검증 결과를 담은 Map 객체 ({"ok": true/false})
     */
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