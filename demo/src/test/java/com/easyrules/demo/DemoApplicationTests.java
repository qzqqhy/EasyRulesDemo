package com.easyrules.demo;

import com.easyrules.demo.data.NovelList;
import com.easyrules.demo.mapper.UserMapper;
import com.easyrules.demo.model.User;
import com.easyrules.demo.rules.MartialArtsRule;
import com.easyrules.demo.rules.XianxiaRule;
import com.easyrules.demo.rules.YesOrNo;
import org.jeasy.rules.api.*;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private NovelList novelList;

    @Test
    void test1() {
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
        facts.put("type", "仙侠");
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
    }

    @Test
    void test3() throws Exception {

        // define facts
        Facts facts = new Facts();


        // define rules
        MVELRuleFactory ruleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader());

        //配置文件规则加载
        BiConsumer<String,Rules> consumer = (path,rules)->{
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
        RulesEngineParameters parameters = rulesEngine.getParameters();

        novelList.getNovels().forEach(a -> {
            facts.put("type", a.getType());
            facts.put("novel", a);
            facts.put("name", a.getName());
            rulesEngine.fire(rules, facts);
        });

    }
    @Test
    public void test4(){

        System.out.println(this.getClass().getClassLoader().getResource(""));
        System.out.println(this.getClass().getClassLoader().getResource("/"));
        System.out.println(this.getClass().getResource(""));
        System.out.println(this.getClass().getResource("/"));

        System.out.println(System.getProperty("user.dir")+"\\main\\resources");

        System.out.println(System.getProperty("java.class.path"));
        System.out.println(System.getProperty("java.compiler"));
        System.out.println(System.getProperty("user.home"));

        System.out.println(Thread.currentThread().getContextClassLoader().getResource("/"));

    }

    @Autowired
    private UserMapper userMapper;

    @org.junit.Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }
}
