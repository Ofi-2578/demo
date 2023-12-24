package com.example.demo.auths;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.Authentication;

import java.security.AlgorithmParameterGenerator;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class JWT {

    public static String create(Authentication authentication){
        Algorithm a = Algorithm.HMAC256("SomeSecret");
        byte []rand = new byte[32];
        new Random().nextBytes(rand);
        String jwtId = Base64.getEncoder().encodeToString(rand);
        return com.auth0.jwt.JWT.create()
                .withClaim("id", authentication.getName())
                .withJWTId(jwtId)
                .sign(a);
    }

    public static String verify(String token){
        JWTVerifier verifier = com.auth0.jwt.JWT.require(Algorithm.HMAC256("SomeSecret")).build();
        return verifier.verify(token).getClaim("id").asString();
    }
}
