package com.epam.hometasksecurity.service;

import com.epam.hometasksecurity.exception.SecretNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SecretService {

    private static final Map<String, String> secretMap = new ConcurrentHashMap<>();

    public void addSecret(String secret, String key) {
        secretMap.put(key, secret);
    }

    public String getSecret(String key) {
        String secret = secretMap.get(key);
        if (secret == null) {
            throw new SecretNotFoundException();
        }
        secretMap.remove(key);
        return secret;
    }
}
