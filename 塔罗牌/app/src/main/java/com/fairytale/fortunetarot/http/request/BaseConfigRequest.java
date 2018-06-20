package com.fairytale.fortunetarot.http.request;

import android.content.Context;
import android.content.Intent;

import com.fairytale.fortunetarot.http.HttpRequestBuilder;
import com.fairytale.fortunetarot.entity.BaseConfigEntity;
import com.fairytale.fortunetarot.http.response.Response;
import com.fairytale.fortunetarot.util.JsonUtils;
import com.fairytale.fortunetarot.util.Logger;
import com.fairytale.fortunetarot.util.SPUtil;

import retrofit2.http.GET;
import rx.Observable;
import rx.functions.Action1;

import static com.fairytale.fortunetarot.comm.Config.CONFIG_GOT;

/**
 * Created by lizhen on 2018/4/2.
 */

public class BaseConfigRequest extends BaseRequest {
    private BaseConfigService mBaseConfigService;

    public BaseConfigRequest(){
        mBaseConfigService = HttpRequestBuilder.getInstance().createService(BaseConfigService.class);
    }

    public Observable<BaseConfigEntity> getBaseConfig() {
        return observe(mBaseConfigService.getBaseConfig());/*.subscribe(new Action1<BaseConfigEntity>() {
            @Override
            public void call(BaseConfigEntity baseConfigEntityResponse) {
                String baseConfig = JsonUtils.entityToJsonString(baseConfigEntityResponse);
                SPUtil.put(context,"BaseConfig",baseConfig);
                SPUtil.put(context,"isGoodOpinionToUnlock",baseConfigEntityResponse.getIshaoping());
//                Intent intent = new Intent();
//                intent.putExtra("isConfigGot",true);
//                intent.setAction(CONFIG_GOT);
//                Logger.e("send","sendTImes" + android.os.Process.myPid());
//                context.sendBroadcast(intent);
            }
        });*/
    }

    private interface BaseConfigService{

        @GET("/webapps/tarot/config/?versoin=5.0&versioncode=5000&channel=appstore")
        Observable<BaseConfigEntity> getBaseConfig();
    }
}
