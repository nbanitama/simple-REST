package com.example.demo.util;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * Created by noba on 2/21/2018.
 */
public class Response<T> {

    private String status;
    private String timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private String getCurrentTime(){
        Date date = new Date();
        return Long.toString(date.getTime());
    }

    public Response() {
        this.timestamp = this.getCurrentTime();
    }

    public String constructMessage(String responseStatus) {
        if(responseStatus.equals(ErrorStatus.NO_DATA))
            return "No data found on server.";
        else if(responseStatus.equals(ErrorStatus.NOT_FOUND))
            return "No specified data found on server.";
        return "";
    }

    public Response(String status) {
        this();
        this.status = status;
        if(!status.equals(ErrorStatus.SUCCESS)) {
            this.message = this.constructMessage(status);
        }
    }

    public Response(T data) {
        this(ErrorStatus.SUCCESS);
        this.data = data;
    }

    public Response(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
