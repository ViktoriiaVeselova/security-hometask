package com.epam.hometasksecurity.security.brutforcedefence;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {
    private final int MAX_ATTEMPT = 3;
    private final int BLOCKED_TIME = 5;
    private final LoadingCache<String, Integer> attemptsCache;

    private final LoadingCache<String, String> blockerUsers;

    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder().
                expireAfterWrite(BLOCKED_TIME, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
        blockerUsers = CacheBuilder.newBuilder().expireAfterWrite(BLOCKED_TIME, TimeUnit.MINUTES).build(new CacheLoader<String, String>() {
            public String load(String key) {
                return "";
            }
        });

    }

    public void loginFailed(String remoteAdress, String username) {
        int attempts = 0;
        try {
            attempts = attemptsCache.get(remoteAdress);
        } catch (ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(remoteAdress, attempts);
        if (attempts >= MAX_ATTEMPT) {
            addBlockedUser(username, remoteAdress);
            System.out.println(blockerUsers.asMap());
        }
    }

    public boolean isBlocked(String key) {
        try {
            return attemptsCache.get(key) >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        }
    }

    public List<String> getBlockedUsers() {
        return blockerUsers.asMap().keySet().stream().toList();
    }

    public void addBlockedUser(String key, String username) {
        blockerUsers.put(key, username);
    }
}
