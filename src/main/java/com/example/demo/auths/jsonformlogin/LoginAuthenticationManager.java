package com.example.demo.auths.jsonformlogin;

import com.example.demo.auths.CustomAuth;
import com.example.demo.services.DefaultPasswordEncoder;
import com.example.demo.user.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public class LoginAuthenticationManager implements ReactiveAuthenticationManager {


    UserRepository userRepository;
    public LoginAuthenticationManager(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return userRepository.findById(authentication.getName())
                .flatMap(user -> {
                    boolean match = DefaultPasswordEncoder.getEncoder().matches(
                            (String) authentication.getCredentials(), user.getPassword()
                    );
                    if (!match) {
                        return Mono.error(new BadCredentialsException("Bad Credentials"));
                    }

                    CustomAuth customAuth = new CustomAuth(
                            (String) authentication.getPrincipal(), null, true
                    );
                    return Mono.just((Authentication) customAuth);
                }).switchIfEmpty(Mono.error(new BadCredentialsException("Bad Credentials")));
    }
}
