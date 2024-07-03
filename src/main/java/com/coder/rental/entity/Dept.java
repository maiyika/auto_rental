package com.coder.rental.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

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
@TableName("sys_dept")
@ApiModel(value = "Dept对象", description = "")
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("部门id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("部门地址")
    private String address;

    @ApiModelProperty("上级部门id")
    private Integer pid;

    @ApiModelProperty("上级部门名称")
    private String parentName;

    @ApiModelProperty("排序号")
    private Integer orderNum;

    @ApiModelProperty("是否删除")
    private Boolean deleted;

    @TableField(exist = false)
    private List<Dept> children;

    @Override
    public String toString() {
        return "Dept{" +
                "id=" + id +
                ", deptName='" + deptName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", pid=" + pid +
                ", parentName='" + parentName + '\'' +
                ", orderNum=" + orderNum +
                ", deleted=" + deleted +
                ", children=" + children +
                '}';
    }
}
