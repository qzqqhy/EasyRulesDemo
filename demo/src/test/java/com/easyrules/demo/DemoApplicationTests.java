package com.easyrules.demo;

import com.easyrules.demo.data.NovelList;
import com.easyrules.demo.rules.MartialArtsRule;
import com.easyrules.demo.rules.XianxiaRule;
import com.easyrules.demo.rules.YesOrNo;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RulesEngineParameters;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

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

}
