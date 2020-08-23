package com.easyrules.demo;

import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;

import java.util.Map;

public class Test {
    public static void main(String []args){
        AbstractTemplateEngine abstractTemplateEngine = new AbstractTemplateEngine() {
            @Override
            public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {

            }

            @Override
            public String templateFilePath(String filePath) {
                return null;
            }
        };
//        abstractTemplateEngine.in

    }
}
