package com.easyrules.demo.rules;

import org.jeasy.rules.annotation.*;

@Rule(priority = 1)
public class MartialArtsRule {
    private String type;


    /**
     * 规则
     * @return
     */
    @Condition
    public boolean isMartialArts(@Fact("type") String type) {
        // can return true here
        return type.equals("武侠");
    }

    /**
     * 执行
     */
    @Action
    public void printInput() {
        System.out.print("\n\n\n所有人修炼内功，希望成名。\n\n\n");
    }
}
