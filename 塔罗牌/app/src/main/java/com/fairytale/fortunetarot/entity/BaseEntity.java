package com.fairytale.fortunetarot.entity;

import com.fairytale.fortunetarot.util.JsonUtils;

/**
 * Created by lizhen on 2018/1/4.
 */

public class BaseEntity {

    @Override
    public String toString() {
        return JsonUtils.entityToJsonString(this);
    }
}
