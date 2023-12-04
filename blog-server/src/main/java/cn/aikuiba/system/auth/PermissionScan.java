package cn.aikuiba.system.auth;

import cn.aikuiba.system.entity.Permission;
import cn.aikuiba.system.mapper.PermissionMapper;
import cn.hutool.core.util.ClassUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by 蛮小满Sama at 2023/11/25 16:07
 *
 * @description
 */
@Component
public class PermissionScan {

    // 扫描路径包前缀
    private static final String PKG_PREFIX = "cn.aikuiba.";
    // 扫描路径包后缀
    private static final String PKG_SUFFIX = ".controller";

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * @Description: 权限初始化业务方法
     * @Author: Neuronet
     * @Date: 2023/10/2 18:11
     * @Return:
     **/
    public void scanPermission() {
        // 1.获取cn.itsource下面所有的模块目录
        String path = Objects.requireNonNull(this.getClass().getResource("/")).getPath() + "cn/aikuiba/";
        File file = new File(path);
        File[] files = file.listFiles(File::isDirectory);
        if (null != files) {
            // 2.获取cn.itsource.*.controller里面所有的类
            Set<Class> clazzes = new HashSet<>();
            for (File fileTmp : files) {
                Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(PKG_PREFIX + fileTmp.getName() + PKG_SUFFIX, RestController.class);
                clazzes.addAll(classes);
            }

            // 3.遍历获取的每个类
            for (Class clazz : clazzes) {
                // 3.1.得到类中的所有方法，如果类中没有方法那么结束本次循环
                Method[] methods = clazz.getMethods();
                if (methods == null || methods.length < 1) {
                    return;
                }
                // 3.2.遍历类中的所有方法
                for (Method method : methods) {
                    // 1.得到该方法的请求地址
                    String uri = getUri(clazz, method);
                    try {
                        // 2.获取到类上我们的自定义注解，判断如果没有此结束本次循环
                        PermissionAnno permissionAnnotation = method.getAnnotation(PermissionAnno.class);
                        if (permissionAnnotation == null) {
                            continue;
                        }
                        // 3.得到我们自定义注解中的name和sn
                        String name = permissionAnnotation.name();
                        String sn = permissionAnnotation.sn();
                        String descs = permissionAnnotation.descs();
                        // 4.根据sn编码查询权限对象
                        Permission permission = permissionMapper.findBySn(sn);
                        // 5.如果不存在就添加权限
                        if (permission == null) {
                            permission = new Permission();
                            permission.setName(name);
                            permission.setSn(sn);
                            permission.setUrl(uri);
                            permission.setDescs(descs);
                            permissionMapper.insert(permission);
                        } else { // 6.如果存在就修改权限
                            permission.setName(name);
                            permission.setSn(sn);
                            permission.setUrl(uri);
                            permission.setDescs(descs);
                            permissionMapper.update(permission);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }


    /**
     * @param clazz:  类字节码对象
     * @param method: 方法对象
     * @Description: 根据类的字节码和类的方法得到方法的请求地址
     * @return: java.lang.String
     **/
    private String getUri(Class clazz, Method method) {
        String classPath = "";
        Annotation annotation = clazz.getAnnotation(RequestMapping.class);
        if (annotation != null) {
            RequestMapping requestMapping = (RequestMapping) annotation;
            String[] values = requestMapping.value();
            if (values != null && values.length > 0) {
                classPath = values[0];
                if (!"".equals(classPath) && !classPath.startsWith("/")) {
                    classPath = "/" + classPath;
                }
            }
        }
        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        String methodPath = "";
        if (getMapping != null) {
            String[] values = getMapping.value();
            if (values != null && values.length > 0) {
                methodPath = values[0];
                if (!"".equals(methodPath) && !methodPath.startsWith("/")) {
                    methodPath = "/" + methodPath;
                }
            }
        }
        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        if (postMapping != null) {
            String[] values = postMapping.value();
            if (values != null && values.length > 0) {
                methodPath = values[0];
                if (!"".equals(methodPath) && !methodPath.startsWith("/")) {
                    methodPath = "/" + methodPath;
                }
            }
        }
        DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
        if (deleteMapping != null) {
            String[] values = deleteMapping.value();
            if (values != null && values.length > 0) {
                methodPath = values[0];
                if (!"".equals(methodPath) && !methodPath.startsWith("/")) {
                    methodPath = "/" + methodPath;
                }
            }
        }
        PutMapping putMapping = method.getAnnotation(PutMapping.class);
        if (putMapping != null) {
            String[] values = putMapping.value();
            if (values != null && values.length > 0) {
                methodPath = values[0];
                if (!"".equals(methodPath) && !methodPath.startsWith("/")) {
                    methodPath = "/" + methodPath;
                }
            }

        }
        PatchMapping patchMapping = method.getAnnotation(PatchMapping.class);
        if (patchMapping != null) {
            String[] values = patchMapping.value();
            if (values != null && values.length > 0) {
                methodPath = values[0];
                if (!"".equals(methodPath) && !methodPath.startsWith("/")) {
                    methodPath = "/" + methodPath;
                }
            }
        }
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null) {
            String[] values = requestMapping.value();
            if (values != null && values.length > 0) {
                methodPath = values[0];
                if (!"".equals(methodPath) && !methodPath.startsWith("/")) {
                    methodPath = "/" + methodPath;
                }
            }
        }
        return classPath + methodPath;
    }
}
