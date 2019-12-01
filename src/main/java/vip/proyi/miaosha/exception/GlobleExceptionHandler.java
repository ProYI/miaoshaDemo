package vip.proyi.miaosha.exception;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import vip.proyi.miaosha.base.ResponseCode;
import vip.proyi.miaosha.base.ResponseModel;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobleExceptionHandler {
    /**
     * 接口不存在
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseModel handler(NoHandlerFoundException e) {
        log.error("接口不存在：{}",e.getMessage());
        return ResponseModel.createByFAILEDCodeMessage(ResponseCode.HTTP_REQUEST_URL_NOT_FOUND.getCode(),ResponseCode.HTTP_REQUEST_URL_NOT_FOUND.getDesc());
    }

    /**
     * 请求方式异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseModel handler(HttpRequestMethodNotSupportedException e) {
        log.error("请求方式不支持：{}",e.getMessage());
        return ResponseModel.createByFAILEDCodeMessage(ResponseCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getCode(), ResponseCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getDesc());
    }

    /**
     * 参数校验不合格
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = BindException.class)
    public ResponseModel handler(BindException e) {
        BindingResult result = e.getBindingResult();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            List<String> errList = errors.stream().map(i -> {
                FieldError fieldError = (FieldError) i;
                log.error("参数检验失败 : object={},field={},errorMessage={}",fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
                return fieldError.getDefaultMessage();
            }).collect(Collectors.toList());

            return ResponseModel.createByFAILEDCodeMessage(ResponseCode.PARAM_VALIDATOR_ERROR.getCode(), ResponseCode.PARAM_VALIDATOR_ERROR.getDesc() + " : " + JSON.toJSONString(errList));
        }
        return ResponseModel.createByFAILEDCodeMessage(ResponseCode.SERVER_ERROR.getCode(), ResponseCode.SERVER_ERROR.getDesc());
    }

    /**
     * 业务异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = ServiceException.class)
    public ResponseModel<String> handler(ServiceException e) {
        log.error("业务异常：{}", e.getMsg());
        return ResponseModel.createByFAILEDCodeMessage(e.getCode(), e.getMsg());
    }

    /**
     * 全局异常捕捉处理
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public ResponseModel<String> handler(Exception e) {
        log.error("服务异常：", e);
        return ResponseModel.createByFAILEDCodeMessage(ResponseCode.SERVER_ERROR.getCode(), ResponseCode.SERVER_ERROR.getDesc());
    }
}
