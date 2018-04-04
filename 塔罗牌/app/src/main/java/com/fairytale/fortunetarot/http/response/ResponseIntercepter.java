package com.fairytale.fortunetarot.http.response;

import com.fairytale.fortunetarot.util.JsonUtils;
import com.fairytale.fortunetarot.util.Logger;

import java.io.IOException;

import okhttp3.*;
import okhttp3.Response;

/**
 * Created by lizhen on 2018/1/12.
 */

public class ResponseIntercepter implements Interceptor {
    private String tag = "Response";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (response.code() != 200 || (response.code() == 200 && response.body() != null && response.body().contentLength() == 0)) {
            com.fairytale.fortunetarot.http.response.Response errorResponse = new com.fairytale.fortunetarot.http.response.Response(
                    response.code(),
                    response.body() == null ? "服务器错误" : response.body().string(),
                    null,response.code());
            return new Response.Builder().code(response.code())
                    .body(ResponseBody.create(response.body() == null ? MediaType.parse("text/json; charset=utf-8") : response.body().contentType(), JsonUtils.entityToJsonString(errorResponse)))
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .message("服务器错误")
                    .build();
        }
        return response;
    }
}
