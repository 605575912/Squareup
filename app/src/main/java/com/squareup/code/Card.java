package com.squareup.code;

import java.util.List;

/**
 * Created by Administrator on 2017/05/31 0031.
 */

public class Card {
    private List<CardUnit> cardUnits;
    private List<ItemData> items;

    public List<CardUnit> getCardUnits() {
        return cardUnits;
    }

    public void setCardUnits(List<CardUnit> cardUnits) {
        this.cardUnits = cardUnits;
    }

    public List<ItemData> getItems() {
        return items;
    }
}
