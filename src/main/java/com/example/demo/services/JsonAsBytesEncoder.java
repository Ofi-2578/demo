package com.example.demo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

public class JsonAsBytesEncoder {
    public static byte[] encode(Object value) {
        try {
           return new ObjectMapper().writeValueAsBytes(value);
        }catch (JsonProcessingException e){
            return new byte[0];
        }
    }
}
