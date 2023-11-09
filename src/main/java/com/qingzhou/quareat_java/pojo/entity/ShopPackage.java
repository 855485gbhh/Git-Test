package com.qingzhou.quareat_java.pojo.entity;


import com.mybatisflex.annotation.Id;

import com.mybatisflex.annotation.Table;
import com.qingzhou.quareat_java.pojo.view.Entity;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Table("qnc_shop_package")
public class ShopPackage {

    @Id
    @NotNull(groups = {Entity.Create.class, Entity.Update.class}, message = "id不可为空")
    @DecimalMin(value = "400000000000000", inclusive = true, groups = {Entity.Create.class, Entity.Update.class}, message = "id不可为空")
    private Long id;  //主键id，唯一标识

    private String name;  //套餐名称

    private String itemList;  //单品id集

    private BigDecimal price;  //套餐价格
    @NotNull(groups = {Entity.Create.class}, message = "创建时间错误")
    @DecimalMin(value = "1600000000", inclusive = true, groups = {Entity.Create.class}, message = "创建时间错误")
    private Timestamp gmtCreate;  //数据创建时间
    @NotNull(groups = {Entity.Create.class, Entity.Update.class}, message = "更新时间错误")
    @DecimalMin(value = "1600000000", inclusive = true, groups = {Entity.Create.class, Entity.Update.class}, message = "更新时间错误")
    private Timestamp gmtModified;  //上一次数据更新时间
    @Range(min = 0, max = 1, groups = {Entity.Update.class}, message = "逻辑删除字段错误")
    private Short isDeleted;  //是否已删除（0：未删除，1：已删除）





}

