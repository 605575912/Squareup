package com.squareup.code.mine;

import com.squareup.code.MineItemData;

import java.util.List;

/**
 * Created by Administrator on 2017/05/31 0031.
 */

public class MineCard {
    private List<MineCardUnit> cardUnits;//一个大卡片
    private LoginCard logincard;


    public LoginCard getLogincard() {
        return logincard;
    }

    public List<MineCardUnit> getCardUnits() {

        return cardUnits;
    }
}
