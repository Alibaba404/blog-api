package cn.aikuiba.exception;

/**
 * Created by 蛮小满Sama at 2023/11/28 14:41
 *
 * @description 自定义异常类
 */
public class BisException extends RuntimeException {
    public BisException() {
    }

    public BisException(String message) {
        super(message);
    }
}
