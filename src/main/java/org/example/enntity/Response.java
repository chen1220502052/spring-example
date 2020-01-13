package org.example.enntity;

import org.example.contant.ResponseCode;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private static final long serialVersionUID = 1;

    private int code = 0;
    private String message = "SUCCESS";
    private T body;

    public final static Response<Object> FAILD_RESPONE = new Response(ResponseCode.PARAM_INVALID, "params are invalid, please check", null);


    public Response(){

    }

    public Response(T body){
        this.body = body;
    }

    public Response(int code, String message, T body){
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", body=" + body +
                '}';
    }
}
