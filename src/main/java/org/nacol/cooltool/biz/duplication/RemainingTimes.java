package org.nacol.cooltool.biz.duplication;

import org.aspectj.lang.annotation.Aspect;
import org.nacol.cooltool.core.lang.BizAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RemainingTimes {

    private static final Logger logger = LoggerFactory.getLogger(RemainingTimes.class);
    private static final String REMAINING_TIMES = "remaining-times";

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public void init(String bizCode, String bizId, int times) {
        String key = initKey(bizCode, bizId);
        redisTemplate.opsForHash().put(REMAINING_TIMES, key, times + "");
        // TODO 过期时间
    }

    public int consume(String bizCode, String bizId) {
        String key = initKey(bizCode, bizId);
        // 获取当前剩余次数
        String timesStr = (String)redisTemplate.opsForHash().get(REMAINING_TIMES, key);
        BizAssert.isNull(timesStr, "不允许重复发送。");

        // 验证是否有剩余次数
        Integer times = Integer.parseInt(timesStr);
        BizAssert.isTrue(times <= 0, "不允许重复发送。");

        // 重置剩余次数
        int remainingTimes =  times - 1;
        init(bizCode, bizId, remainingTimes);

        // TODO 0 则清除 key
        return times - 1;
    }

    private String initKey(String bizCode, String bizdId) {
        return new StringBuffer(bizCode).append("-").append(bizdId).toString();
    }

}
