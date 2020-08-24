package com.easyrules.demo.rules;

import com.alibaba.fastjson.JSONObject;
import com.easyrules.demo.model.SysErRules;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author liuxiu
 * @since 2020-08-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EasyRule {

    private static final long serialVersionUID = 1L;

    private String name;

    private String description;

    private String condition;

    private List<String> actions;

    public static List<EasyRule> getEasyRuleList(List<SysErRules> list) {

        List<EasyRule> easyRuleList = new ArrayList<>();

        if (CollectionUtils.isEmpty(list)) {
            return easyRuleList;
        }
        list.forEach(a -> {
            if (StringUtils.isBlank(a.getErActions()) || StringUtils.containsNone(a.getErActions(), EasyRuleEnum.EASYRULESPLIT.getValue())) {
                return;
            }
            EasyRule easyRule = new EasyRule(a.getErName(), a.getErDescription(), a.getErCondition());

            List<String> fas = Arrays.asList(a.getErActions().split(EasyRuleEnum.EASYRULESPLIT.getValue()));
            easyRule.setActions(fas);
            easyRuleList.add(easyRule);
        });
        return easyRuleList;
    }

    public EasyRule(String name, String description, String condition, List<String> actions) {
        this.name = name;
        this.description = description;
        this.condition = condition;
        this.actions = actions;
    }

    public EasyRule(String name, String description, String condition) {
        this.name = name;
        this.description = description;
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }
}
