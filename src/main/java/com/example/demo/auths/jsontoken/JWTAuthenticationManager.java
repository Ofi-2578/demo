package com.example.demo.auths.jsontoken;

import com.example.demo.auths.CustomAuth;
import com.example.demo.auths.JWT;
import com.example.demo.user.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public class JWTAuthenticationManager implements ReactiveAuthenticationManager {
    UserRepository userRepository;
    public JWTAuthenticationManager(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String id = JWT.verify((String) authentication.getCredentials());
        return userRepository
                .findById(id).flatMap(user -> {
                    CustomAuth customeAuth = new CustomAuth(user.getUsername(), null, true);
                    return Mono.just((Authentication) customeAuth);
                }).switchIfEmpty(Mono.defer( () -> Mono.error(new BadCredentialsException("Bad Credentialsss"))));

    }
}
