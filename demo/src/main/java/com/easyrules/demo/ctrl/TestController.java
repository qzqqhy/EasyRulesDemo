package com.easyrules.demo.ctrl;

import com.alibaba.fastjson.JSON;
import com.easyrules.demo.data.NovelList;
import com.easyrules.demo.rules.MartialArtsRule;
import com.easyrules.demo.rules.XianxiaRule;
import com.easyrules.demo.rules.YesOrNo;
import org.jeasy.rules.api.*;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RulesEngineParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONStringer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by hzlbo on 2016/7/2.
 */
@RestController
public class TestController {

    @Autowired
    private NovelList novelList;

    @RequestMapping("/novelList")
    @ResponseBody
    public String novelList() throws Exception {

        return JSON.toJSONString(novelList.getNovels());
    }

    @RequestMapping("/novelTypeList")
    @ResponseBody
    public String getNovelTypes() throws Exception {

        return JSON.toJSONString(novelList.getNovelTypes());
    }

    @RequestMapping("/select")
    @ResponseBody
    public String select(String type) {

        // create a rules engine
        RulesEngineParameters parameters = new RulesEngineParameters().skipOnFirstAppliedRule(true);
        RulesEngine fizzBuzzEngine = new DefaultRulesEngine(parameters);

        // create rules
        Rules rules = new Rules();
        rules.register(new MartialArtsRule());
        rules.register(new XianxiaRule());

        // fire rules
        YesOrNo isEndFor = new YesOrNo(YesOrNo.yes);

        Facts facts = new Facts();
        facts.put("type", type);
        Map<Rule, Boolean> check = fizzBuzzEngine.check(rules, facts);

        check.forEach((m, b) -> {
            if (b) {
                try {
                    m.execute(facts);
                    isEndFor.setYesNo(YesOrNo.no);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return "ok";
    }

}
