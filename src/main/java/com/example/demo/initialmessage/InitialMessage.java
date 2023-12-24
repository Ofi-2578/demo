package com.example.demo.initialmessage;

import com.fasterxml.jackson.annotation.JsonView;

public class InitialMessage {
    public InitialMessage(String identity, String epk, String[] preKeys, String text, long n, String path, String receiver) {
        this.identity = identity;
        this.epk = epk;
        this.preKeys = preKeys;
        this.text = text;
        this.n = n;
        this.path = path;
        this.receiver = receiver;
    }

    @JsonView(InitialMessageView.class)
    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @JsonView(InitialMessageView.class)
    public String getEpk() {
        return epk;
    }

    public void setEpk(String epk) {
        this.epk = epk;
    }

    @JsonView(InitialMessageView.class)
    public String[] getPreKeys() {
        return preKeys;
    }

    public void setPreKeys(String[] preKeys) {
        this.preKeys = preKeys;
    }

    @JsonView(InitialMessageView.class)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonView(InitialMessageView.class)
    public long getN() {
        return this.n;
    }

    public void setN(long n) {
        this.n = n;
    }

    @JsonView(InitialMessageView.class)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public interface InitialMessageView{};
    private String identity;
    private String epk;
    private String []preKeys;
    private String text;
    private long n;
    private String path;
    private String receiver;
}

