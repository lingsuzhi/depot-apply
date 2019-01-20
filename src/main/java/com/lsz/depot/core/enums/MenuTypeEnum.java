package com.lsz.depot.core.enums;

/**
 * 菜单类型
 */
public enum MenuTypeEnum {

    CONTENT("1","目录"),
    MENU("2","菜单"),
    BUTTON("3","按钮");

    private String description;
    private String code;

    MenuTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public String getCode() {
        return code;
    }
}
