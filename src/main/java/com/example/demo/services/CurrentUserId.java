package com.example.demo.services;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


public class CurrentUserId {

    public static Mono<String> getId(){
        return ReactiveSecurityContextHolder.getContext().map(s -> s.getAuthentication().getName());
    }
}
