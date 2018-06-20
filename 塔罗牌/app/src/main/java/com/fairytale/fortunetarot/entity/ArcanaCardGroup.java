package com.fairytale.fortunetarot.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lizhen on 2018/4/18.
 */

public class ArcanaCardGroup implements Serializable {
    private String typeName;
    private ArrayList<ArcanaCards> cards = new ArrayList<>();

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public ArrayList<ArcanaCards> getCards() {
        return cards;
    }

    public void setCards(ArrayList<ArcanaCards> cards) {
        this.cards = cards;
    }
}
