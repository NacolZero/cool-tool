package org.nacol.cooltool.utils.result;

/**
 * @Author Nacol(姚秋实)
 * @Date 2022/11/9
 * @Description 返回体状态码
 */
public enum ResultStatus {

    /**
     * 状态
     */
    OK("200", "正确"),
    REPEAT_REQUEST("319","重复请求"),
    BAD_REQUEST("400", "错误的请求"),
    UNAUTHORIZED("401", "禁止访问"),
    NO_PERMISSION("403","没有权限"),
    NOT_FOUND("404", "没有可用的数据"),
    INTERNAL_SERVER_ERROR("500", "服务器遇到了一个未曾预料的状况"),
    SERVICE_UNAVAILABLE("503", "服务器当前无法处理请求"),
    BIZ_EXCEPTION("998", "业务异常");

    /**
     * 状态码,长度固定为6位的字符串.
     */
    private String code;

    /**
     * 错误信息.
     */
    private String reason;

    ResultStatus(final String code, final String reason) {
        this.code = code;
        this.reason = reason;
    }

    public String getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return code + ": " + reason;
    }

}
