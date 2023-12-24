package com.example.demo.auths;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuth implements Authentication {
    private final String principal;
    private final String credentials;
    private final boolean authenticated;


    public CustomAuth(String principal, String credentials, boolean authenticated){
        this.principal = principal;
        this.credentials = credentials;
        this.authenticated = authenticated;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return (String) this.getPrincipal();
    }
}
