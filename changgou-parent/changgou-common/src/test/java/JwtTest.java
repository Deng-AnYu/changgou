import io.jsonwebtoken.*;
import org.junit.Test;

import java.util.Date;

/**
 * @Author: Deng
 * @date: 2020-05-16 20:39
 * @description:
 */
public class JwtTest {

    @Test
    public void test(){
        JwtBuilder builder = Jwts.builder();
        builder.setId("999")
                .setSubject("嘿嘿嘿")
                .setIssuedAt(new Date())
                .setExpiration(new Date())
                .signWith(SignatureAlgorithm.HS256,"itcast");
        System.out.println(builder.compact());
    }

    @Test
    public void testParseJwt(){
        String compact="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5OTkiLCJzdWIiOiLlmL_lmL_lmL8iLCJpYXQiOjE1ODk2MzM0NzEsImV4cCI6MTU4OTYzMzQ3MX0.7OgfPhTDow5HlQhlwOsgvAvVaLvq6RJc-yEECUx381w\n";
        JwtParser parser = Jwts.parser();
        JwtParser itcast = parser.setSigningKey("itcast");
        Jws<Claims> claimsJws = itcast.parseClaimsJws(compact);
        Claims body = claimsJws.getBody();
        Claims claims = Jwts.parser().
                setSigningKey("itcast").
                parseClaimsJws(compact).
                getBody();
        System.out.println(body);
        System.out.println(claims);
    }

}
