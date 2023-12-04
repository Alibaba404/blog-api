package cn.aikuiba.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 百度认证工具类：注意要修改API Key 和 Secret Key
 */
@Slf4j
public class BaiduAuditUtils {
    /**
     * 获取权限token
     *
     * @return 返回示例：
     * {
     * "access_token": "24.3265383f84ac64db9eff781e70587614.2592000.1654844607.282335-26205415",
     * "expires_in": 2592000
     * }
     */
    public static String getAuth() {
        // 官网获取的 API Key 更新为你注册的
        String ak = "R3SW6apHVW9fbDOqNmmHLcQa";
        // 官网获取的 Secret Key 更新为你注册的
        String sk = "VHxQgo4IK7Wz2NfwcoE0aPUo8CGfhCKl";
        return getAuth(ak, sk);
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     *
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.err.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            // 返回结果示例
//            log.info("result:" + result);
            JSONObject jsonObject = JSONObject.parseObject(result.toString());
            return jsonObject.getString("access_token");
        } catch (Exception e) {
            log.error("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }


    public static Boolean textCensor(String param) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/solution/v1/text_censor/v2/user_defined";
        try {
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAuth();
            //处理参数格式
            param = "text=" + param;
            //后端发送请求：通过eoken令牌将审核数据传递给url进行审核，审核完成返回数据是一个json格式字符串
            String result = HttpUtil.post(url, accessToken, param);
            //阿里巴巴的fastJson将json格式的字符串转成json对象
            JSONObject jsonObject = JSONObject.parseObject(result);
            //从json对象中获取属性名为conclusion的内容：合规，不合规
            return "合规".equals(jsonObject.getString("conclusion"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param imageUrl 需要审核的图片路径地址
     * @return
     */
    public static Boolean imgCensor(String imageUrl) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/solution/v1/img_censor/v2/user_defined";
        try {
            //图片地址进行编码
            String imgParam = URLEncoder.encode(imageUrl, StandardCharsets.UTF_8);
            //设置参数
            String param = "imgUrl=" + imgParam;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAuth();

            String result = HttpUtil.post(url, accessToken, param);
            JSONObject jsonObject = JSONObject.parseObject(result);

            String conclusion = jsonObject.getString("conclusion");
            if ("合规".equals(conclusion)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(BaiduAuditUtils.getAuth());
        System.out.println(BaiduAuditUtils.textCensor("操"));//false
        System.out.println(BaiduAuditUtils.textCensor("cnm"));//false
        System.out.println(BaiduAuditUtils.textCensor("sb"));//false
        System.out.println(BaiduAuditUtils.textCensor("牛逼"));//true
        System.out.println(BaiduAuditUtils.textCensor("日寇"));//true
        System.out.println(BaiduAuditUtils.textCensor("公司"));//true
    }
}
