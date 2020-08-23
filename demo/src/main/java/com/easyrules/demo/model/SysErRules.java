package com.easyrules.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class SysErRules extends Model<SysErRules> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String erName;

    private String erDescription;

    private String erCondition;

    private String erActions;


    public static final String ID = "id";

    public static final String ER_NAME = "er_name";

    public static final String ER_DESCRIPTION = "er_description";

    public static final String ER_CONDITION = "er_condition";

    public static final String ER_ACTIONS = "er_actions";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
