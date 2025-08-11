package com.sakura.demo.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class R {
    private Boolean success;                  // 返回是否成功
    private Integer code;                    // 返回状态码
    private String message;                  // 提示信息
    private Map<String, Object> data = new HashMap<>();  // 返回数据

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    // 私有构造，防止外部直接new
    private R() {}

    // 成功静态方法
    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    // 失败静态方法
    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }
    //使用下面四个方法，方面以后使用链式编程//R.okO.success(true)
    //r. message("ok). data("item", list)
    // 链式设置success
    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    // 链式设置message
    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    // 链式设置code
    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    // 链式添加单个键值对数据
    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    // 链式设置整个map数据
    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    @Override
    public String toString() {
        return "R{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

