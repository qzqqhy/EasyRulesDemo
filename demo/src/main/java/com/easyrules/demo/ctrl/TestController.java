package com.easyrules.demo.ctrl;

import com.alibaba.fastjson.JSON;
import com.easyrules.demo.data.NovelList;
import com.easyrules.demo.rules.MartialArtsRule;
import com.easyrules.demo.rules.XianxiaRule;
import com.easyrules.demo.rules.YanqingRule;
import com.easyrules.demo.rules.YesOrNo;
import org.jeasy.rules.api.*;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

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
        rules.register(new YanqingRule());

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
    @RequestMapping("/readfilepaths")
    @ResponseBody
    public List<String> readfilepaths() {
        List<String> files = new ArrayList<String>();
        File file = new File( this.getClass().getClassLoader().getResource("").getPath()+"/rules/");
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                files.add(tempList[i].toString());
            }
        }
        return files;
    }

    @RequestMapping("/pzfileFor")
    @ResponseBody
    public List<String> pzfileFor() {
        // define facts
        Facts facts = new Facts();


        // define rules
        MVELRuleFactory ruleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader());

        //配置文件规则加载
        BiConsumer<String,Rules> consumer = (path, rules)->{
            Rule c_rule = null;
            try {
                c_rule = ruleFactory.createRule(new FileReader(path));
            } catch (Exception e) {
                //TODO 日志打印;
            }
            rules.register(c_rule);
        };

        //规则配置文件循环读取 & 加载规则
        File file = new File( this.getClass().getClassLoader().getResource("").getPath()+"/rules/");
        File[] tempList = file.listFiles();
        Rules rules = new Rules();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                consumer.accept(tempList[i].toString(),rules);
            }
        }

        // fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();

        List<String> res = new ArrayList();

        novelList.getNovels().forEach(a -> {
            facts.put("type", a.getType());
            facts.put("novel", a);
            facts.put("name", a.getName());
            facts.put("res",res);
            rulesEngine.fire(rules, facts);
        });

        return res;
    }

}
