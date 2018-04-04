package com.fairytale.fortunetarot.http.request;

import com.fairytale.fortunetarot.http.HttpRequestBuilder;
import com.fairytale.fortunetarot.http.response.Response;
import com.fairytale.fortunetarot.entity.HistoryEntity;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lizhen on 2018/1/9.
 */

public class HistoryRequest extends BaseRequest{
    private HistoryRequestService mHistoryService;

    public HistoryRequest(){
        mHistoryService = HttpRequestBuilder.getInstance().createService(HistoryRequestService.class);
    }

    public Observable<Response<List<HistoryEntity>>> getTodayInHistory(String month, String day) {
        return observe(mHistoryService.getTodayInHistory( month, day));
    }

    private interface HistoryRequestService{

        @POST("index.php/?main_page=lssdjt_list")
        @FormUrlEncoded
        Observable<Response<List<HistoryEntity>>> getTodayInHistory(@Field("month") String month, @Field("day") String day);
    }

}

