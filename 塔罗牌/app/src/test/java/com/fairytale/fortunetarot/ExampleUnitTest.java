package com.fairytale.fortunetarot;

import com.fairytale.fortunetarot.http.request.HistoryRequest;
import com.fairytale.fortunetarot.entity.HistoryEntity;
import com.fairytale.fortunetarot.http.response.Response;
import com.fairytale.fortunetarot.util.Util;

import org.junit.Test;

import java.util.List;

import rx.functions.Action1;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void testHistory() {
        HistoryRequest mHistoryRequest = new HistoryRequest();
        RxTools.asyncToSync();
        mHistoryRequest.getTodayInHistory("2", "1").subscribe(new Action1<Response<List<HistoryEntity>>>() {
            @Override
            public void call(Response<List<HistoryEntity>> listResponse) {

            }
        });
    }

    @Test
    public void testSpace() {
        String test = "我房间都搜附近的说";
        String result = Util.stringInsertWithTag(test,2,"\n");
        System.out.print(result);
    }

    @Test
    public void test() {
        System.out.print("我的" + "\n" + "我" );
    }
}