package app.exceptions;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ExceptionResponse {

    private String message;
    private String error;
    private Integer status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private Map<String, Object> jsonMap;

    /*
        //  Json Response Sample
        {
            "timestamp": "30-04-2021 04:00:19"
            "status": 404,
            "error": "NOT_FOUND",
            "message": "Corrugation.CORRUGATION_NOT_FOUND with id: 999",
            "jsonMap": { "key" : "value" }
        }
    */

    public ExceptionResponse() {
        this.jsonMap = new HashMap<>();
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "message='" + message + '\'' +
                ", error='" + error + '\'' +
                ", status=" + status +
                ", timestamp=" + timestamp +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getJsonMap() {
        return jsonMap;
    }

}