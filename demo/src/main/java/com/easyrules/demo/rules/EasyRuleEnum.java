package com.easyrules.demo.rules;

/**
 * @ClassName：EasyRuleEnum
 * @Description： <p> EasyRule所有魔法值枚举 </p>
 * @Author： - liuxiu
 * @CreatTime：2020-08-23 - 18:36
 * @Modify By：
 * @ModifyTime： 2020-08-23
 * @Modify marker：
 * @version V1.0
*/
public enum EasyRuleEnum {


    EASYRULESPLIT(100,"@","easyrule actions 代码分隔符,在数据库里应用");

    private int code;
    private String value;
    private String desc;

    EasyRuleEnum(int code,String value){
        this.code = code;
        this.value=value;
    }
    EasyRuleEnum(int code,String value,String desc){
        this.code = code;
        this.value=value;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }}
