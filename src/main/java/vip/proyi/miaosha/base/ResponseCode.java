package vip.proyi.miaosha.base;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS(200, "成功"),
    FAILED(400, "失败"),

    SERVER_ERROR(500100, "服务异常"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(500101, "接口请求方式不支持"),
    HTTP_REQUEST_URL_NOT_FOUND(500102, "请求接口地址不存在"),
    PARAM_VALIDATOR_ERROR(500103, "参数校验异常"),

    // 登录相关 5002XX
    SESSION_ERROR(500210, "session不存在或者已经失效"),
    PASSWORD_EMPTY(500211, "登录密码不能为空"),
    MOBILE_EMPTY(500212, "手机号码不能为空"),
    MOBILE_ERROR(500213, "手机号码格式错误"),
    MOBILE_NOT_EXIST(500214, "手机号码不存在"),
    PASSWORD_ERROR(500215, "账户或密码错误"),

    // 秒杀相关
    MIAOSHA_STOCK_OVER(500300, "该商品已售罄"),
    MIAOSHA_NO_REPEATE(500301, "不能重复秒杀"),
    ;

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
