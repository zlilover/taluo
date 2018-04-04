package com.fairytale.fortunetarot.http.response;

/**
 * Created by lizhen on 2018/1/12.
 */

public class Response<T> {

    private int result;
    private String resultinfo;
    private T infos;
    private int code = 200;

    public Response(int result, String resultinfo, T infos,int code) {
        this.result = result;
        this.resultinfo = resultinfo;
        this.infos = infos;
        this.code = code;
    }

    public Response() {

    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getResultinfo() {
        return resultinfo;
    }

    public void setResultinfo(String resultinfo) {
        this.resultinfo = resultinfo;
    }

    public T getInfos() {
        return infos;
    }

    public void setInfos(T infos) {
        this.infos = infos;
    }
}
