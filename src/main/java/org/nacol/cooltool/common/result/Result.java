package org.nacol.cooltool.common.result;

import lombok.Data;

/**
 * @Author Nacol(姚秋实)
 * @Date 2022/11/9
 * @Description 返回体
 */
@Data
public class Result<T> {

    private static final long serialVersionUID = -105755889014186227L;

    /**
     * 状态码
     */
    private String status;

    /**
     * 描述信息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    private Result(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private Result(String status, T data){
        this.status = status;
        this.data = data;
    }

    private Result(String status, String message) {
        this.status = status;
        this.message = message;
    }

    private Result(String message) {
        this.message = message;
    }

    /*********************************************** 构建【普通】返回结果 ***********************************************/
    public static <T> Result<T> build(ResultStatus ResultStatus, String message, T data) {
        return new Result<T>(ResultStatus.getCode(), message, data);
    }

    public static <T> Result<T> build(ResultStatus ResultStatus, String message) {
        return new Result<T>(ResultStatus.getCode(), message);
    }

    public static <T> Result<T> build(ResultStatus ResultStatus, T data) {
        return new Result<T>(ResultStatus.getCode(), ResultStatus.getReason(), data);
    }

    public static <T> Result<T> build(ResultStatus ResultStatus) {
        return new Result<T>(ResultStatus.getCode(), ResultStatus.getReason());
    }

    /*********************************************** 构建【成功】返回结果 ***********************************************/
    public static <T> Result<T> success(){

        return new Result<T>(ResultStatus.OK.getCode(), ResultStatus.OK.getReason());
    }

    public static <T> Result<T> success(T data){
        return new Result<T>(ResultStatus.OK.getCode(), ResultStatus.OK.getReason(), data);
    }

    public static <T> Result<T> success(String message){
        return new Result<T>(ResultStatus.OK.getCode(), message);
    }

    public static <T> Result<T> success(String message, T data){
        return new Result<T>(ResultStatus.OK.getCode(), message, data);
    }

    /*********************************************** 构建【异常】返回结果 ***********************************************/
    public static <T> Result<T> fail(){
        return new Result<T>(ResultStatus.INTERNAL_SERVER_ERROR.getCode(), ResultStatus.INTERNAL_SERVER_ERROR.getReason());
    }

    public static <T> Result<T> fail(T data){
        return new Result<T>(ResultStatus.INTERNAL_SERVER_ERROR.getCode(), ResultStatus.INTERNAL_SERVER_ERROR.getReason(), data);
    }

    public static <T> Result<T> fail(String message){
        return new Result<T>(ResultStatus.INTERNAL_SERVER_ERROR.getCode(), message);
    }

    public static <T> Result<T> fail(String message, T data){
        return new Result<T>(ResultStatus.INTERNAL_SERVER_ERROR.getCode(), message, data);
    }

    /*********************************************** 构建【业务异常】返回结果 ***********************************************/
    public static <T> Result<T> bizException(){
        return new Result<T>(ResultStatus.BIZ_EXCEPTION.getCode(), ResultStatus.INTERNAL_SERVER_ERROR.getReason());
    }

    public static <T> Result<T> bizException(T data){
        return new Result<T>(ResultStatus.BIZ_EXCEPTION.getCode(), ResultStatus.BIZ_EXCEPTION.getReason(), data);
    }

    public static <T> Result<T> bizException(String message){
        return new Result<T>(ResultStatus.BIZ_EXCEPTION.getCode(), message);
    }

    public static <T> Result<T> bizException(String message, T data){
        return new Result<T>(ResultStatus.BIZ_EXCEPTION.getCode(), message, data);
    }

}
