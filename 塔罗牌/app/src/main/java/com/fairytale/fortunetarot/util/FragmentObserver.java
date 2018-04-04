package com.fairytale.fortunetarot.util;

/**
 * Created by lizhen on 2018/2/8.
 */

public interface FragmentObserver {

    /**
     *
     * @param hasContent 是否有数据
     * @param isNormal  是否正常情况，例如是没找到内容还是因为网络请求出错，没找到内容为true，网络请求出错为false
     */
    void onChange(boolean hasContent, boolean isNormal);
}
