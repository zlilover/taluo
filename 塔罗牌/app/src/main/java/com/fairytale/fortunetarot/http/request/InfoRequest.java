package com.fairytale.fortunetarot.http.request;

import com.fairytale.fortunetarot.entity.InfoEntity;
import com.fairytale.fortunetarot.http.HttpRequestBuilder;
import com.fairytale.fortunetarot.http.response.Response;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lizhen on 2018/2/7.
 */

public class InfoRequest extends BaseRequest {
    private InfoRequestService mInfoRequestService;

    public InfoRequest() {
        mInfoRequestService = HttpRequestBuilder.getInstance().createService(InfoRequestService.class);
    }

    public Observable<Response<List<InfoEntity>>> getInfoByType(int type, int page) {
        return observe(mInfoRequestService.getInfoByType(type,page));
    }
    private interface InfoRequestService {
        @POST("index.php?main_page=taluozixun_list")
        @FormUrlEncoded
        Observable<Response<List<InfoEntity>>> getInfoByType(@Field("leibie") int leibie,@Field("page") int page);
    }
}
