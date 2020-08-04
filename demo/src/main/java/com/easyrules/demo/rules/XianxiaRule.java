package com.easyrules.demo.rules;

import org.jeasy.rules.annotation.*;

@Rule(priority = 1)
public class XianxiaRule {
    private String type;


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

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 优先级 TODO 作用暂时未知
     * @return
     */
    @Priority
    public int getPriority() {
        return 2;
    }
}
