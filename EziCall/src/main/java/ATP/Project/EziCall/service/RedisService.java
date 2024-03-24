package ATP.Project.EziCall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void storeToken(String token, long expirationTime) {
        redisTemplate.opsForValue().set(token, "blacklisted", expirationTime, TimeUnit.SECONDS);
    }

    public boolean isTokenBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }

}
