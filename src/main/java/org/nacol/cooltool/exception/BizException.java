package org.nacol.cooltool.exception;

/**
 * @Author Nacol(姚秋实)
 * @Date 2023/1/26
 * @Description 业务异常
 */
public class BizException extends RuntimeException{

    private Object data;

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Object data) {
        super(message);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
