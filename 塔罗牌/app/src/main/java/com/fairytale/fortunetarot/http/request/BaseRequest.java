package com.fairytale.fortunetarot.http.request;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lizhen on 2018/1/11.
 */

public class BaseRequest {

    protected <T> Observable<T> observe(Observable<T> observable) {
//        单元测试时开启
//        Scheduler scheduler = RxAndroidPlugins.getInstance().getSchedulersHook().getMainThreadScheduler();
//        return observable.subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .subscribeOn(scheduler);

        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
