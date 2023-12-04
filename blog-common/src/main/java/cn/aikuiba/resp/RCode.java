package cn.aikuiba.resp;

/**
 * Created by 蛮小满Sama at 2023/11/30 12:02
 *
 * @description
 */
public enum RCode {
    NOT_LOGIN("未登录", 2003),
    TOKEN_INVALIDATION("登录过期,请重新登录", 2004),
    APP_ARTICLE_COMMENT_NON_COMPLIANCE("评论包含低俗或侮辱词汇", 3001);
    private final String message;

    private final Integer code;

    RCode(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public String message() {
        return this.message;
    }

    public Integer code() {
        return this.code;
    }

}
