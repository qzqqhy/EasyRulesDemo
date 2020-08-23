package com.easyrules.demo.ctrl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easyrules.demo.mapper.SysErRulesMapper;
import com.easyrules.demo.model.SysErRules;
import com.easyrules.demo.rules.EasyRule;
import com.easyrules.demo.rules.LoadEasyRules;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liuxiu
 * @since 2020-08-23
 */
@Controller
@RequestMapping("/demo/sysErRules")
public class SysErRulesController {
    @Autowired(required = false)
    SysErRulesMapper sysErRulesMapper;

    private Rules rules = null;

    @PostConstruct
    private void init() throws Exception {
        Wrapper<SysErRules> query = Wrappers.query();
        List<SysErRules> sysErRulesList = sysErRulesMapper.selectList(query);

        LoadEasyRules loadEasyRules = LoadEasyRules.newLoadEasyRules();
        // define rules
        this.rules = new Rules();
        loadEasyRules.loadList(rules,EasyRule.getEasyRuleList(sysErRulesList));
    }

    @RequestMapping("/query")
    @ResponseBody
    public String getNovelTypes() throws Exception {

        Wrapper<SysErRules> query = Wrappers.query();
        return JSON.toJSONString(sysErRulesMapper.selectList(query));
    }

    /**
    * @description: 重新加载规则
    * @param
    * @return
    * @throws
    * @author liuxiu
    * @date 2020-08-23 18:10
    */
    @RequestMapping("/easyrule")
    @ResponseBody
    public String easyrule() throws Exception {

        Wrapper<SysErRules> query = Wrappers.query();
        List<SysErRules> sysErRulesList = sysErRulesMapper.selectList(query);

        LoadEasyRules loadEasyRules = LoadEasyRules.newLoadEasyRules();
        this.rules = new Rules();
        // define rules
        loadEasyRules.loadList(this.rules,EasyRule.getEasyRuleList(sysErRulesList));

        return "ok";
    }

    /**
    * @description: 执行规则
    * @param
    * @return
    * @throws
    * @author liuxiu
    * @date 2020-08-23 18:10
    */
    @RequestMapping("/run")
    @ResponseBody
    public String run(String condition) throws Exception {

        // define facts
        Facts facts = new Facts();

        RulesEngine rulesEngine = new DefaultRulesEngine();

        Map<String,Object> map = new HashMap<>();

        facts.put("bool",condition);
        facts.put("map",map);


        rulesEngine.fire(rules, facts);

        return JSONObject.toJSONString(map);
    }

    public static void main(String []args){
        EasyRule easyRule = new EasyRule("test","test","bool == false", Arrays.asList("System.out.print(11111);"));

        List<EasyRule> list = new ArrayList();
        list.add(easyRule);

        // define facts
        Facts facts = new Facts();

        RulesEngine rulesEngine = new DefaultRulesEngine();



        LoadEasyRules loadEasyRules = LoadEasyRules.newLoadEasyRules();

        Rules rules = new Rules();

        loadEasyRules.load(rules,easyRule);

        facts.put("bool",false);
        rulesEngine.fire(rules, facts);
        System.out.println(rules);
        //
    }
}

