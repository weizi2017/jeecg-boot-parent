package org.jeecg.modules.exam.redis;

/**
 * @author zbm
 * @date 2019-05-31
 */
public class RedisKeys {
    public static String getUserExamKey(String userId,String examId,String quesId) {
        return String.format("exam_%s_%s_%s", userId,examId,quesId);
    }

}
