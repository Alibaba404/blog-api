package cn.aikuiba.system.jwt;

import cn.aikuiba.system.entity.Logininfo;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.RegisteredPayload;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 蛮小满Sama at 2023/11/28 14:38
 *
 * @description
 */
@Slf4j
public class JwtUtil {
    // JWT所需要的密钥
    public static final String KEY = "aikuiba";
    // 设置Token过期时间：3秒 和 30分钟
    public static final int EXPIRE_SECOND = 3;
    public static final int EXPIRE_MINUTE = 30;

    public static String generateToken(Payload payload) {
        Map<String, Object> map = new HashMap<>();
        map.put("payload", payload);
        return generateToken(map);
    }

    /**
     * 生成token
     *
     * @param data data
     * @return String
     */
    public static String generateToken(Map<String, Object> data) {
        DateTime now = DateTime.now();
        DateTime newTime = now.offsetNew(DateField.MINUTE, JwtUtil.EXPIRE_MINUTE);
        Map<String, Object> payload = new HashMap<>();
        //签发时间
        payload.put(RegisteredPayload.ISSUED_AT, now);
        //过期时间
        payload.put(RegisteredPayload.EXPIRES_AT, newTime);
        //生效时间
        payload.put(RegisteredPayload.NOT_BEFORE, now);
        //荷载数据
        payload.putAll(data);
        String token = JWTUtil.createToken(payload, JwtUtil.KEY.getBytes());
        log.info("生成token->" + token);
        return token;
    }

    /**
     * 验证token
     *
     * @param token token
     * @return JSONObject
     */
    public static Payload parseJwtToken(String token) {
        return parseJwtToken(JwtUtil.KEY, token);
    }

    /**
     * 验证token
     *
     * @param key   key
     * @param token token
     * @return JSONObject
     */
    public static Payload parseJwtToken(String key, String token) {
        JWT jwt = JWTUtil.parseToken(token);
        jwt.setKey(key.getBytes()).verify();
        boolean verifyTime = jwt.validate(0);
        if (!verifyTime) {
            throw new RuntimeException("token过期");
        }
        Object payloadObj = jwt.getPayload("payload");
        return JSONObject.parseObject(payloadObj.toString(), Payload.class);
    }


    public static void main(String[] args) throws InterruptedException {
        Logininfo logininfo = new Logininfo();
        logininfo.setId(999L);
        logininfo.setUsername("唐伯虎");
        Payload payload = new Payload();
        payload.setLogininfo(logininfo);
        String token = generateToken(payload);
        System.out.println(token);

        Payload p = parseJwtToken(token);
        System.out.println(p.getLogininfo());

        Thread.sleep(5000);
        p = parseJwtToken(token);
        System.out.println(p.getLogininfo());
    }
}
