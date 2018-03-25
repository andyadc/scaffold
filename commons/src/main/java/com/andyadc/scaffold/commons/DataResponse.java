package com.andyadc.scaffold.commons;

/**
 * @author andy.an
 * @since 2018/3/25
 */
public class DataResponse<T> extends Response {

    private static final long serialVersionUID = 1L;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataResponse{" +
                "data=" + data +
                "} " + super.toString();
    }
}
