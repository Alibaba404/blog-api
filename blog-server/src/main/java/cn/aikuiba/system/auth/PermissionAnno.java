package cn.aikuiba.system.auth;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 蛮小满Sama at 2023/11/25 16:01
 *
 * @description
 */
@Target({ElementType.METHOD})//注解能作用在方法上、类上
//Java中的反射：在运行时，动态获取类的各种信息的一种能力
@Retention(RetentionPolicy.RUNTIME)//可以通过反射读取注解
@Inherited//可以被继承
@Documented//可以被javadoc工具提取成文档，可以不加
public @interface PermissionAnno {
    String name();

    String sn();

    String descs() default "";
}
