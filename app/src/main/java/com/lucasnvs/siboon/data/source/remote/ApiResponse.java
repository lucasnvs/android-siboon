package com.lucasnvs.siboon.data.source.remote;

import java.util.Objects;

public class ApiResponse<T> {
    private String status;
    private T data;

    public boolean isError() {
        return Objects.equals(getStatus(), "success");
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
}
