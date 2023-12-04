package cn.aikuiba.system.interceptor;

import cn.aikuiba.resp.RCode;
import cn.aikuiba.system.jwt.JwtUtil;
import cn.aikuiba.system.jwt.Payload;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 蛮小满Sama at 2023/11/30 11:55
 *
 * @description
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (StrUtil.isNotBlank(request.getHeader("FROMAPP"))) return true;
        if (StrUtil.isNotBlank(request.getParameter("FROMAPP"))) return true;
        String token = request.getHeader("token");
        JSONObject resultMap = new JSONObject();
        if (StrUtil.isBlank(token)) {
            resultMap.set("success", Boolean.FALSE);
            resultMap.set("code", RCode.NOT_LOGIN.code());
            resultMap.set("message", RCode.NOT_LOGIN.message());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(resultMap);
            return false;
        }
        try {
            JwtUtil.parseJwtToken(token);
            return true;
        } catch (Exception e) {
            resultMap.set("success", Boolean.FALSE);
            resultMap.set("code", RCode.TOKEN_INVALIDATION.code());
            resultMap.set("message", RCode.TOKEN_INVALIDATION.message());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(resultMap);
            e.printStackTrace();
            return false;
        }
    }
}
