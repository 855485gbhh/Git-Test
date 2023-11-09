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
@Table("qnc_chat")
public class Chat {

    @Id
    @NotNull(groups = {Entity.Create.class, Entity.Update.class}, message = "id不可为空")
    @DecimalMin(value = "400000000000000", inclusive = true, groups = {Entity.Create.class, Entity.Update.class}, message = "id不可为空")
    private Long id;  //主键id，唯一标识

    private Long fatherChatId;  //父对话id，为0则为一级对话

    private Long userId;  //发布者用户id

    private String content;  //对话内容

    private BigDecimal publisherLatitude;  //发布者纬度

    private BigDecimal publisherLongitude;  //发布者经度
    @NotNull(groups = {Entity.Create.class}, message = "创建时间错误")
    @DecimalMin(value = "1600000000", inclusive = true, groups = {Entity.Create.class}, message = "创建时间错误")
    private Timestamp gmtCreate;  //数据创建时间
    @NotNull(groups = {Entity.Create.class, Entity.Update.class}, message = "更新时间错误")
    @DecimalMin(value = "1600000000", inclusive = true, groups = {Entity.Create.class, Entity.Update.class}, message = "更新时间错误")
    private Timestamp gmtModified;  //上一次更新数据时间
    @Range(min = 0, max = 1, groups = {Entity.Update.class}, message = "逻辑删除字段错误")
    private Short isDeleted;  //是否已删除（0：未删除，1：已删除）





}

