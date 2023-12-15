package com.finalproject.bttd.apiresponse;

import lombok.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor

public class ApiResponse<T> {

    public static final String SUCCESS_STATUS = "true";
    public static final String FAIL_STATUS = "fail";
    public static final String ERROR_STATUS = "error";

    public String status;
    public T data;
    public String message;





    public ApiResponse(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
