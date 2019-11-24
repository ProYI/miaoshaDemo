package vip.proyi.miaosha.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import vip.proyi.miaosha.base.ResponseCode;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**

 * 〈通用返回类型〉
 *
 */
@Data
public class ResponseModel<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 每一次接口返回类型的json格式统一为
     * message:XXX
     * data:XXX
     */
    private String message;
    private Integer code;
    private T data;

    private ResponseModel(Integer code) {
        this.code = code;
    }

    private ResponseModel(Integer code, T data) {
        this.data = data;
        this.code = code;
    }

    private ResponseModel(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public ResponseModel(Integer code, String message, T data) {
        this.data = data;
        this.message = message;
        this.code = code;
    }

    /**
     * status是0 返回true
     * status不是0 返回false
     *
     * JsonIgnore不让其值显示在返回的json数据中
     */
    @JsonIgnore
    public boolean isSuccess() {
        return this.code == ResponseCode.SUCCESS.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static <T> ResponseModel<T> createBySuccess() {
        return new ResponseModel<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ResponseModel<T> createBySuccessMessage(String msg) {
        return new ResponseModel<T>(ResponseCode.SUCCESS.getCode(), msg);
    }
    public static <T> ResponseModel<T> createBySuccess(T data) {
        return new ResponseModel<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> ResponseModel<T> createBySuccess(String msg, T data) {
        return new ResponseModel<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ResponseModel<T> createByFAILED() {
        return new ResponseModel<T>(ResponseCode.FAILED.getCode(),ResponseCode.FAILED.getDesc());
    }
    public static <T> ResponseModel<T> createByFAILEDMessage(String errorMessage) {
        return new ResponseModel<T>(ResponseCode.FAILED.getCode(), errorMessage);
    }
    public static <T> ResponseModel<T> createByFAILEDCodeMessage(int errorCode, String errorMessage) {
        return new ResponseModel<T>(errorCode, errorMessage);
    }
}