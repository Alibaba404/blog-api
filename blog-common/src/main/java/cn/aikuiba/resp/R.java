package cn.aikuiba.resp;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 蛮小满Sama at 2023/11/18 10:55
 *
 * @description
 */
@Data
@AllArgsConstructor
public class R<T> {

    private Integer code;

    private Boolean success;

    private String message;

    private String info;

    private T data;

    // 私有化无参构造器
    private R() {
    }

    private static <T> R<T> common(Integer code, Boolean success, String message, String info, T data) {
        return new R<>(code, success, message, info, data);
    }

    public static <T> R<T> success(Integer code, String message) {
        return common(code, Boolean.TRUE, message, "", null);
    }

    public static <T> R<T> success() {
        return success(200, "SUCCESS");
    }

    public static <T> R<T> success(T data) {
        return common(200, Boolean.TRUE, "SUCCESS", "", data);
    }

    public static <T> R<T> failure(Integer code, String message, T data) {
        return common(code, Boolean.FALSE, message, "", data);
    }

    public static <T> R<T> failure(Integer code, String message) {
        return failure(code, message, null);
    }

    public static <T> R<T> failure(Integer code, String message, String info) {
        return common(code, Boolean.FALSE, message, info, null);
    }

}
