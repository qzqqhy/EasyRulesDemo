package com.easyrules.demo.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

@Rule(priority = 2)
public class YanqingRule {


    /**
     * 规则
     * @return
     */
    @Condition
    public boolean isMartialArts(@Fact("type") String type) {
        // can return true here
        return type.equals("言情");
    }

    /**
     * 执行
     */
    @Action
    public void printInput() {
        System.out.print("\n\n\n所有人都在搞对象。\n\n\n");
    }

}
