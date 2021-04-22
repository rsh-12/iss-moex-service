package ru.task.iss.exception;
/*
 * Date: 4/21/21
 * Time: 3:28 PM
 * */

import java.util.Date;

public final class ErrorResponse {

    private Date timestamp;
    private int httpStatus;
    private String error;
    private String message;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "timestamp=" + timestamp +
                ", httpStatus=" + httpStatus +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
