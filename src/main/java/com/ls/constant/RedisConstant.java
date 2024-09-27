package com.ls.constant;

/**
 * title: RedisConstant
 * author: liaoshuo
 * package: com.ls.constant
 * date: 2024/9/23 16:12
 * description: redis 常量
 */
public interface RedisConstant {

    String USER_SIGN_IN_REDIS_KEY_PREFIX = "user:signin";

    /**
     * 获取用户登录 Redis 密钥前缀
     *
     * @param year   年
     * @param userId 用户 ID
     * @return 返回拼接好的key
     */
    static String getUserSignInRedisKeyPrefix(int year, long userId) {
        return String.format("%s:%s:%s", USER_SIGN_IN_REDIS_KEY_PREFIX, year, userId);
    }
}
