package com.squareup.code

import com.squareup.code.CardUnit
import com.squareup.code.ItemData
import com.squareup.code.column.ColumnData
import com.squareup.code.discount.DiscountData
import com.squareup.code.home.banner.BannerModel

/**
 * Created by Administrator on 2017/05/31 0031.
 */

class Card {
    var cardUnits: List<CardUnit>? = null
    val items: List<ItemData>? = null
    val banners: List<BannerModel>? = null
    val columnitems: List<ColumnData>? = null
    val discountdatas: List<DiscountData>? = null
}