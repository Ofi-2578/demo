package com.example.demo.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

public class DefaultPasswordEncoder {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public static PasswordEncoder getEncoder(){
        return passwordEncoder;
    }
}
