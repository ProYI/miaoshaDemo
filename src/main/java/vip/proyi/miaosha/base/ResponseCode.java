package vip.proyi.miaosha.base;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS(200, "成功"),
    FAILED(400, "失败"),
    ;

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
