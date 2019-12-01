package vip.proyi.miaosha.exception;

import lombok.Data;
import vip.proyi.miaosha.base.ResponseCode;

@Data
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -4308904920529303976L;

    private int code;
    private String msg;

    public ServiceException(ResponseCode response) {
        this.code = response.getCode();
        this.msg = response.getDesc();
    }
}
