package com.squareup.code;

import com.squareup.lib.utils.IProguard;

import java.util.List;

/**
 * Created by Administrator on 2017/05/31 0031.
 */

public class DataUnit implements IProguard.ProtectMembers {
    private int titletype = -1;//-1 w无  1  搜索
    private List<Card> cards;//大卡片
    private List<ItemData> items;//一组Item


    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<ItemData> getItems() {
        return items;
    }

    public void setItems(List<ItemData> items) {
        this.items = items;
    }

    public int getTitletype() {
        return titletype;
    }
}
