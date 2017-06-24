package com.squareup.code;

import com.squareup.code.column.ColumnData;
import com.squareup.code.discount.DiscountData;
import com.squareup.code.home.banner.BannerModel;

import java.util.List;

/**
 * Created by Administrator on 2017/05/31 0031.
 */

public class Card {
    private List<CardUnit> cardUnits;
    private List<ItemData> items;
    private List<BannerModel> banners;
    private List<ColumnData> columnitems;
    private List<DiscountData> discountdatas;
    public List<BannerModel> getBanners() {
        return banners;
    }

    public List<ColumnData> getColumnitems() {
        return columnitems;
    }

    public List<CardUnit> getCardUnits() {
        return cardUnits;
    }

    public void setCardUnits(List<CardUnit> cardUnits) {
        this.cardUnits = cardUnits;
    }

    public List<ItemData> getItems() {
        return items;
    }

    public List<DiscountData> getDiscountdatas() {
        return discountdatas;
    }
}
