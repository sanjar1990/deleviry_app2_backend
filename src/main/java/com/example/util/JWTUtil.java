package com.example.util;

import com.example.dto.JwtDTO;
import com.example.exceptions.UnAuthorizedException;
import io.jsonwebtoken.*;

import java.util.Date;

public class JWTUtil {
    private static final String secretKey="mazgi123";
    private static int tokenLiveTime=1000 * 3600 * 24;

    public static String encode(String id, String email){
        JwtBuilder jwtBuilder= Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);
        jwtBuilder.claim("id",id);
        jwtBuilder.claim("email",email);

        jwtBuilder.setExpiration(new Date(System.currentTimeMillis()+(tokenLiveTime)));
        jwtBuilder.setIssuer("Food delivery");
        return jwtBuilder.compact();
    }

    public static JwtDTO decode(String token){
        try {
            JwtParser jwtParser=Jwts.parser();
            jwtParser.setSigningKey(secretKey);
            Jws<Claims>jws=jwtParser.parseClaimsJws(token);
            Claims claims= jws.getBody();
            String email=(String) claims.get("email");
            String id=(String) claims.get("id");

            return new JwtDTO(id,email);
        }catch (ExpiredJwtException e){
            throw new UnAuthorizedException("your session is expired");
        }catch (JwtException e){
            throw new UnAuthorizedException(e.getMessage());
        }
    }
}