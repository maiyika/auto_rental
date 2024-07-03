package com.coder.rental.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Barry
 * @since 2024-06-16
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_role")
@ApiModel(value = "Role对象", description = "")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("角色编码")
    private String roleCode;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("创建人id")
    private Integer createrId;

    @ApiModelProperty("创建时间")
    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否删除")
    private Boolean deleted;
}
