package com.example.board2.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {
    //JWT 생성 및 검증을 위한 키
    //jwt를 쓸때 사용할 키로 이걸 쓰겠다. 데이터 암호화 복호화할떄 씀
    private static final String SECURITY_KEY = "jwtseckey!@";

    //jwt를 생성하는 메서드
    public String create (String userEmail) {
        //만료날짜 세팅: 현재 시간에 1시간 더해줌.
        Date exprTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        //JWT 생성
        return Jwts.builder() //jwt
                .signWith(SignatureAlgorithm.HS512, SECURITY_KEY) //HS512로 암호화 알고리즘적용, 사용할 키는 SECURITY_KEY,
                .setSubject(userEmail)  //무엇을 암호화 할것인가. 제목.
                .setIssuedAt(new Date())  //생성날짜
                .setExpiration(exprTime)  //만료일
                .compact();  //만들어서 jwt돌려주는, 즉 생성
    }

    //암호화된 것을 복구.
    public String validate (String token) {
        //매개변수로 받은 token을 키를 사용해서 복호화(디코딩)
        Claims claims = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();
        //복호화된 토큰의 payload에서 제목을 가져옴.
        return claims.getSubject(); //생성할때 넣었던 userEmail을 받아올 수 있음
    }
}
