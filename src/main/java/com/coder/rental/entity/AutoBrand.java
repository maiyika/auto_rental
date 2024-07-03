package com.coder.rental.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@TableName("auto_brand")
@ApiModel(value = "AutoBrand对象", description = "")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AutoBrand implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("品牌id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("厂商id")
    private Integer mid;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("是否删除")
    private Boolean deleted;
    @Override
    public String toString() {
        return "AutoBrand{" +
                "id=" + id +
                ", mid=" + mid +
                ", brandName='" + brandName + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
