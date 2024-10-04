package com.intbyte.wizbuddy.user.security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class HMACKeyGenerator {

    public static void main(String[] args) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA512");
            keyGen.init(512); // 512비트 키 사이즈 설정

            SecretKey secretKey = keyGen.generateKey();

            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

            System.out.println("HS512 Key: " + encodedKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}