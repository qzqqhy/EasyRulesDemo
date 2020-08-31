package com.easyrules.demo;

import com.easyrules.demo.utils.UnicodeConverteUtil;

public class Test {
    public static void main(String []args){
//        com.easyrules.demo.utils.HttpClientUtil.doHttpPost()
        System.out.println(EncodeUtils.unicodeToCn("111asdfffwsidj\\u597d\\uff0c\\u53ef\\u4ee5\\u5916\\u51fa\\u6d3b\\u52a8\\uff0c\\u547c\\u5438\\u65b0\\u9c9c\\u7a7a"));
        System.out.println(UnicodeUtil.decode("333222\\u597d\\uff0c\\u53ef\\u4ee5\\u5916\\u51fa\\u6d3b\\u52a8\\uff0c\\u547c\\u5438\\u65b0\\u9c9c\\u7a7a"));
        System.out.println(UnicodeConverteUtil.unicode2String("333222\\u597d\\uff0c\\u53ef\\u4ee5\\u5916\\u51fa\\u6d3b\\u52a8\\uff0c\\u547c\\u5438\\u65b0\\u9c9c\\u7a7a"));
    }
}
