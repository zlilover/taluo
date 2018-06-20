package com.fairytale.fortunetarot.entity;

import java.io.Serializable;

/**
 * Created by lizhen on 2018/4/18.
 */

public class ArcanaCards implements Serializable {
    private String name_for_icon;
    private String name;
    private String name_eng;
    private String Constellation;
    private String roma_num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_eng() {
        return name_eng;
    }

    public void setName_eng(String name_eng) {
        this.name_eng = name_eng;
    }

    public String getConstellation() {
        return Constellation;
    }

    public void setConstellation(String constellation) {
        Constellation = constellation;
    }

    public String getName_for_icon() {
        return name_for_icon;
    }

    public void setName_for_icon(String name_for_icon) {
        this.name_for_icon = name_for_icon;
    }

    public String getRoma_num() {
        return roma_num;
    }

    public void setRoma_num(String roma_num) {
        this.roma_num = roma_num;
    }
}
