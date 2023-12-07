package cn.aikuiba.system.aop;

import cn.aikuiba.system.entity.Logs;
import cn.aikuiba.system.jwt.JwtUtil;
import cn.aikuiba.system.jwt.Payload;
import cn.aikuiba.system.mapper.LogsMapper;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by 蛮小满Sama at 2023/12/3 10:20
 *
 * @description 日志AOP配置
 */
@Slf4j
@Component
@Aspect
public class LogsConfig {
    @Autowired
    private LogsMapper logsMapper;

    @Autowired
    private HttpServletRequest request;

    // 定义切点
    @Pointcut("@annotation(cn.aikuiba.system.aop.LogsAnno)")
    public void pointcut() {
    }

    // 定义通知
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) {
        try {
            // 操作的类名
            String className = pjp.getTarget().getClass().getName();
            // 操作的方法名
            String methodName = pjp.getSignature().getName();
            // 操作方法的参数列表
            Object[] args = pjp.getArgs();
            List<Map<String, String>> dataMaps = new ArrayList<>();
            for (Object arg : args) {
                if (arg == null) continue;
                Map<String, String> obj = new HashMap<>();
                BeanInfo beanInfo = Introspector.getBeanInfo(arg.getClass(), Object.class);
                // 获取属性描述列表
                PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor pd : pds) {
                    // 获取可读属性方法
                    Method readMethod = pd.getReadMethod();
                    // 调用获取到的方法,并获取返回值
                    Object value = readMethod.invoke(arg);
                    if (null == value) continue;
                    if (StrUtil.isBlank(String.valueOf(value))) continue;
                    if (value instanceof ArrayList<?> a && a.size() == 0) continue;
                    obj.put(pd.getName(), String.valueOf(value));
                }
                obj.put("entity", arg.getClass().getName());
                dataMaps.add(obj);
            }
            String argsString = dataMaps.toString();
            if (StrUtil.isNotBlank(argsString) && argsString.length() > 200) {
                argsString = argsString.substring(0, 200);
            }

            // 方法执行开始毫秒值
            long millis_begin = System.currentTimeMillis();
            // 操作方法的返回值
            Object proceed = pjp.proceed();
            // 方法执行结束时毫秒值
            long millis_finish = System.currentTimeMillis();
            // 日志对象
            Logs logs = new Logs();
            logs.setCreateTime(new Date());
            logs.setClassName(className);
            logs.setMethodName(methodName);
            logs.setMethodParams(argsString);
            logs.setReturnValue(String.valueOf(proceed));
            logs.setCostTime(millis_finish - millis_begin);
            logs.setIp(request.getRemoteAddr());
            String token = request.getHeader("Token");
            if (StrUtil.isNotBlank(token)) {
                Payload payload = JwtUtil.parseJwtToken(token);
                if (payload != null) {
                    logs.setUserId(payload.getLogininfo().getId());
                    logs.setUserName(payload.getLogininfo().getUsername());
                }
            }
            logsMapper.insert(logs);
            return proceed;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
