package com.changgou.token;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

public class ParseJwtTest {

    /***
     * 校验令牌
     */
    @Test
    public void testParseToken(){
        //令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6IlJPTEVfVklQMixST0xFX1VTRVIyIiwibmFtZSI6IkFBQUFBIiwiaWQiOiIxMiJ9.Lwsh1DWB2yDVW5JfK_6HFX5fFwtDrlk3pQ4jE0UDavXNf807WRIfAjl3_lin4CkNkKEIOMwrrLhLuJHskIZbJGKCQ5-D8Xa-Dzt8w8OFp-x4sXFRQfFevjmVKiZD0gwFWXSiTRox9Q77Yv3P0gOomqnjqQFelNA-kTRbdEJE3sJBDdK4DFu6POaRUJUIs5O1_lpr0JyAJu7MOJupW4THhq-NA2iFWeR4jF_A9fzbbjiiQHynovPrz-aentl7xdjZKlG7ytpR--P4PWKxfa0V_qqR0FlU5V-4gg5r8O3kSkphywbS7iMrehZMXm_s7usuCDZZ9GrN9Halk6bLN6Tieg";

        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg4yLbRBtROJaqV03ISiycNIW7+r9c42EYCPN1p1/2mS5IkJCzgmxa0ai/huzkD+i1plh4kVlHhgZhWB7zp0B8FklPvKfojsPmQOA3pmt7WheDX4Rk2aCcbhwdG8f3bjH5rkytpeocUMtMcnNrqhM4mFgn4JxBba6JNrS0SF7evGzmIOGm4a1mcWVea48JfHdUmNQralhZJH75GUlcbepKQGxRkrvRMiExsS5m7+Wm2JYN9EFPZZc9HgRNUcZS46otkL5Kx+J/w68YFXp1vpipKxE4BtqIILjhfKm/ENchtI00eRQ2KdTbB+S3Amn8QOcplMvQ/qHE7OmoivS5wt5kwIDAQAB-----END PUBLIC KEY-----";

        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));

        //获取Jwt原始内容
        String claims = jwt.getClaims();
        System.out.println(claims);
        //jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}