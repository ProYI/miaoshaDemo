package vip.proyi.miaosha.exception;

import vip.proyi.miaosha.base.ResponseCode;
import vip.proyi.miaosha.base.ResponseModel;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -4308904920529303976L;

    private ResponseModel response;

    public ServiceException(ResponseModel response) {
        this.response = response;
    }

    public ResponseModel getResult() {
        return response;
    }

    public ServiceException(String message) {
        this.response.setCode(ResponseCode.FAILED.getCode());
        this.response.setMessage(message);
        this.response.setData(null);
    }
}
