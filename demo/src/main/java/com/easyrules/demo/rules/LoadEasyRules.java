package com.easyrules.demo.rules;

import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.RuleDefinition;
import org.jeasy.rules.support.reader.RuleDefinitionReader;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;
import org.mvel2.ParserContext;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @ClassName：LoadEasyRules
 * @Description： <p> 加载EasyRules 类，继承MVELRuleFactory 为了使用 里面的 createSimpleRule 方法</p>
 * @Author： - liuxiu
 * @CreatTime：2020-08-23 - 17:02
 * @Modify By：
 * @ModifyTime： 2020-08-23
 * @Modify marker：
 * @version V1.0
*/
public class LoadEasyRules extends MVELRuleFactory {

    public LoadEasyRules(RuleDefinitionReader reader) {
        super(reader);
    }

    
    public LoadEasyRules(RuleDefinitionReader reader, ParserContext parserContext) {
        super(reader, parserContext);
    }



    public static LoadEasyRules newLoadEasyRules() {
        return new LoadEasyRules(new YamlRuleDefinitionReader());
    }


    /**
    * @description: 加载单个集合
    * @param
    * @return
    * @throws
    * @author liuxiu
    * @date 2020-08-23 17:10
    */
    public void load(Rules rules, EasyRule easyRule) {

        LoadEasyRules loadEasyRules = this.newLoadEasyRules();

        RuleDefinition simplerule = new RuleDefinition();
        simplerule.setCondition(easyRule.getCondition());
        simplerule.setActions(easyRule.getActions());
        simplerule.setDescription(easyRule.getDescription());
        simplerule.setName(easyRule.getName());
        Rule c_rule = loadEasyRules.createSimpleRule(simplerule);
        rules.register(c_rule);

    }

    /**
    * @description: 加载规则集合
    * @param
    * @return
    * @throws
    * @author liuxiu
    * @date 2020-08-23 17:06
    */
    public void loadList(Rules rules, List<EasyRule> easyRules) throws Exception{

        if(CollectionUtils.isEmpty(easyRules)){
            throw new Exception("规则集合为空");
        }

        LoadEasyRules loadEasyRules = this.newLoadEasyRules();

        easyRules.forEach(a->{
            RuleDefinition simplerule = new RuleDefinition();
            simplerule.setCondition(a.getCondition());
            simplerule.setActions(a.getActions());
            simplerule.setDescription(a.getDescription());
            simplerule.setName(a.getName());
            Rule c_rule = loadEasyRules.createSimpleRule(simplerule);
            rules.register(c_rule);
        });


    }
}
