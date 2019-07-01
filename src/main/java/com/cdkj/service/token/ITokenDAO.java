package com.cdkj.service.token;

import org.springframework.data.redis.connection.RedisConnection;

public interface ITokenDAO {

    public Object doInRedis(RedisConnection connection);

    public Token getToken(String tokenId);

    public void saveToken(Token token);

    public abstract long delToken(String tokenId);

}
