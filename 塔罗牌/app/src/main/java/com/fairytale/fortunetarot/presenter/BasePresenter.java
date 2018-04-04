package com.fairytale.fortunetarot.presenter;

import com.fairytale.fortunetarot.http.request.HttpRequestManager;
import com.fairytale.fortunetarot.http.response.Response;
import com.fairytale.fortunetarot.util.Logger;
import com.fairytale.fortunetarot.view.BaseView;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by lizhen on 2018/1/4.
 */

public class BasePresenter{
    protected BaseView mBaseView;
    protected int[] tags;

    public BasePresenter(BaseView baseView){
        this.mBaseView = baseView;
    }

    public void startRequestByCode(int httpCode) {
        mBaseView.loadingDialog();
    }

    void success(Response data){
        mBaseView.finishLoading();
    }

    private void error(Response response){
        mBaseView.finishLoading();
        switch (response.getCode()) {
            case 401:   // UNAUTHORIZED
            case 403:   // FORBIDDEN
                perssionError();
                break;
            case 404:   // NOT_FOUND
                apiPathError();
                break;
            case 408:   // REQUEST_TIMEOUT
            case 500:   // INTERNAL_SERVER_ERROR
            case 502:   // BAD_GATE_WAY
            case 503:   // SERVICE_UNAVAILABLE
            case 504:   // GATEWAY_TIMEOUT
                serviceEndError();
                break;
            default:
                appError();
                break;
        }
    }

    void appError() {}

    void perssionError() {}

    void apiPathError() {}

    void serviceEndError() {}

    public void finishRequest() {
        mBaseView.finishLoading();
    }

    void subscribe(Object tag, Subscription subscription) {
        HttpRequestManager.getInstance().add(tag, subscription);
    }

    public void cancelAll() {
        HttpRequestManager.getInstance().cancelByTags(tags);
    }

    public void cancelByTag(int tag) {
        HttpRequestManager.getInstance().cancel(tag);
    }

    class ResponseFilter<T> implements Action1<Response<T>> {

        @Override
        public void call(Response<T> tResponse) {
            if (tResponse.getCode() != 200) {
                error(tResponse);
            }
            else {
                success(tResponse);
            }
        }
    }

    Action1<Throwable> errorAction = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            Logger.e("throwable",throwable.toString());
            error(new Response(0,null,null,-1));
        }
    };

}
