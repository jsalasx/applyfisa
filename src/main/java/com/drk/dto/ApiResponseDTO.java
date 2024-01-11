package com.drk.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseDTO<T> {
    public T data;
    public String msg;
    public Boolean error;
    public ApiResponseDTO<T> ApiResponseDTOOk(T data) {
        this.data = data;
        this.msg = "";
        this.error = false;
        return this;
    }

    public ApiResponseDTO<T> ApiResponseError(String msg) {
        this.data = null;
        this.msg = msg;
        this.error = true;
        return this;
    }
}
