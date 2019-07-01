package com.cdkj.service.token.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.cdkj.service.exception.BizException;
import com.cdkj.service.token.ITokenDAO;
import com.cdkj.service.token.Token;

@Component
public class TokenDAOImpl<T> implements ITokenDAO {

    @Autowired
    protected RedisTemplate<Serializable, Serializable> redisTemplate;

    @Override
    public Object doInRedis(RedisConnection connection) {
        // TODO Auto-generated method stub
        return null;
    }

    /** 
     * @see com.cdkj.service.token.ITokenDAO#getToken(java.lang.String)
     */
    @Override
    public Token getToken(final String tokenId) {
        try {
            return redisTemplate.execute(new RedisCallback<Token>() {
                @Override
                public Token doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    byte[] key = redisTemplate.getStringSerializer().serialize(
                        "token.tid." + tokenId);
                    if (connection.exists(key)) {
                        // byte[] value = connection.get(key);
                        // String tokenValue =
                        // redisTemplate.getStringSerializer()
                        // .deserialize(value);
                        Token token = new Token();
                        token.setTokenId(tokenId);
                        return token;
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            throw new BizException("xn000000", "token服务错误，请联系管理人员");
        }
    }

    /** 
     * @see com.cdkj.service.token.ITokenDAO#saveToken(com.cdkj.service.token.Token)
     */
    @Override
    public void saveToken(final Token token) {
        try {
            redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    byte[] tokenByte = redisTemplate.getStringSerializer()
                        .serialize("token.tid." + token.getTokenId());
                    connection.set(tokenByte, redisTemplate
                        .getStringSerializer().serialize(token.getTokenId()));
                    connection.expire(tokenByte, 60 * 60 * 24 * 15); // 失效时间15天
                    return null;
                }
            });
        } catch (Exception e) {
            throw new BizException("xn000000", "token服务错误，请联系管理人员");
        }
    }

    /** 
     * @see com.cdkj.service.token.ITokenDAO#delToken(java.lang.String)
     */
    @Override
    public long delToken(final String tokenId) {
        try {
            return redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    long result = 0;
                    byte[] key = redisTemplate.getStringSerializer().serialize(
                        "token.tid." + tokenId);
                    if (connection.exists(key)) {
                        result = connection.del(key);
                        return result;
                    }
                    return result;
                }
            });
        } catch (Exception e) {
            throw new BizException("xn000000", "token服务错误，请联系管理人员");
        }
    }
}
