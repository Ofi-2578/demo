package com.example.demo.user;

import com.example.demo.services.DefaultPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class UserController {


    UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/check")
    public Mono<ResponseEntity> check(){
        Mono<String> getId = ReactiveSecurityContextHolder.getContext().map(s -> s.getAuthentication().getName());
        return getId.flatMap(id -> {
            System.out.println("Done" + id);
            return Mono.just(ResponseEntity.status(204).build());
        });
    }

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> signup(@RequestBody UserForm userForm){
        return userRepository
                .insert(new User(userForm.username(), DefaultPasswordEncoder.getEncoder().encode(userForm.password())))
                .onErrorReturn(new EmptyUser())
                .flatMap(user -> {
                    HttpStatus stats = user instanceof EmptyUser ?
                            HttpStatus.CONFLICT
                            :
                            HttpStatus.NO_CONTENT;
                    return Mono.just(ResponseEntity.status(stats).build());
                });
    }
}
