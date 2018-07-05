package com.zhuzichu.module_cartoon.enums;

import com.zhuzichu.module_base.widget.sidemenu.interfaces.Resourceble;

/**
 * 作者: Zzc on 2018-06-28.
 * 版本: v1.0
 */
public enum TagType implements Resourceble{

    TAG_0(0, "全部"),
    TAG_1(19, "日常"),
    TAG_2(20, "恋爱"),
    TAG_3(22, "奇幻"),
    TAG_4(23, "剧情"),
    TAG_5(24, "爆笑"),
    TAG_6(27, "治愈"),
    TAG_7(40, "完结"),
    TAG_8(41, "三次元"),
    TAG_9(46, "古风"),
    TAG_10(47, "校园"),
    TAG_11(48, "都市"),
    TAG_12(49, "少年"),
    TAG_13(52, "总裁"),
    TAG_14(53, "栏目"),
    TAG_15(54, "正能量"),
    TAG_16(56, "传统"),
    TAG_17(57, "日漫");
    private int type;
    private String title;


    TagType(int type, String title) {
        this.type = type;
        this.title = title;
    }

    @Override
    public int getImageRes() {
        return this.type;
    }

    @Override
    public String getName() {
        return this.title;
    }
}
