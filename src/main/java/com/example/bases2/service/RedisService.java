package com.example.bases2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveTransactionMessage(String message ) {
        TransactionMessage transactionMessage = new TransactionMessage(message);
        long timestamp = System.currentTimeMillis();
        String key = "transaction:" + timestamp;
        //redisTemplate.opsForValue().set(key, transactionMessage);
    }

    public TransactionMessage getTransactionMessage(String key) {
        return (TransactionMessage) redisTemplate.opsForValue().get(key);
    }
}
