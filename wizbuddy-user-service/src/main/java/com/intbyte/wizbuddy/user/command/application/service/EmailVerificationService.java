package com.intbyte.wizbuddy.user.command.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class EmailVerificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;  // StringRedisTemplate 사용

    private final long VERIFICATION_CODE_TTL = 5; // 5분


    public void sendVerificationEmail(String email) {
        String verificationCode = generateVerificationCode();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Wizbuddy 이메일 인증 코드");
        message.setText("안녕하세요,\n\n"
                + "Wizbuddy를 이용해주셔서 감사합니다. 아래 인증 코드를 입력하여 이메일 인증을 완료해 주세요.\n\n"
                + "인증 코드: " + verificationCode + "\n\n"
                + "인증 코드는 5분 동안 유효합니다.\n"
                + "궁금한 사항이 있으시면 언제든지 문의해 주세요.\n\n"
                + "감사합니다,\n"
                + "Wizbuddy 팀");
        mailSender.send(message);

        saveVerificationCode(email, verificationCode);
    }

    //필기. 해당 이메일의 코드가 일치하는지 확인하는 코드
    public boolean verifyCode(String email, String code) {
        String savedCode = stringRedisTemplate.opsForValue().get(email);  //필기. Redis에서 코드 가져오기

        // 필기. 인증 코드가 일치하면 Redis에서 해당 키값(email)을 True로 설정
        if (savedCode != null && savedCode.equals(code)) {
            // 인증 성공 시 Redis에 True 저장하고 TTL을 1시간으로 설정
            stringRedisTemplate.opsForValue().set(email, "True", 1, TimeUnit.HOURS);
            return true;
        } else {
            return false;
        }
    }

    //필기. Redis에 코드 저장
    private void saveVerificationCode(String email, String code) {
        stringRedisTemplate.opsForValue().set(email, code, VERIFICATION_CODE_TTL, TimeUnit.MINUTES);
    }

    //필기. 6자리 난수 발생 (0~999999)
    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(999999);
        return String.format("%06d", code);
    }
}
