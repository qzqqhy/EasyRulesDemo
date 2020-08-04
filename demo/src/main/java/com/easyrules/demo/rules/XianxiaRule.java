package com.easyrules.demo.rules;

import org.jeasy.rules.annotation.*;

@Rule(priority = 2)
public class XianxiaRule {


    /**
     * 规则
     * @return
     */
    @Condition
    public boolean isMartialArts(@Fact("type") String type) {
        // can return true here
        return type.equals("仙侠");
    }

    /**
     * 执行
     */
    @Action
    public void printInput() {
        System.out.print("\n\n\n所有人修炼灵气，希望成神永生。\n\n\n");
    }

}
