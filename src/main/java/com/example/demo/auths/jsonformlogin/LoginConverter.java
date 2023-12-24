package com.example.demo.auths.jsonformlogin;

import com.example.demo.auths.CustomAuth;
import com.example.demo.user.UserForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;

public class LoginConverter implements ServerAuthenticationConverter {
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return
                exchange
                .getRequest()
                .getBody()
                .map( dataBuffer ->{
                    ObjectMapper om = new ObjectMapper();
                    try {
                        DataBufferUtils.release(dataBuffer);
                        UserForm uf = om.readValue(dataBuffer.asInputStream(), UserForm.class);
                        System.out.println(uf.username() + " " + uf.password());
                        return (Authentication) new CustomAuth(uf.username(), uf.password(), false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                )
                .next();
    }
}
