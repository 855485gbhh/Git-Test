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
@Table("qnc_discuss")
public class Discuss {

    @Id
    @NotNull(groups = {Entity.Create.class, Entity.Update.class}, message = "id不可为空")
    @DecimalMin(value = "400000000000000", inclusive = true, groups = {Entity.Create.class, Entity.Update.class}, message = "id不可为空")
    private Long id;  //主键id，唯一标识

    private Long fatherDiscussId;  //父评论id，为0则为一级评论

    private Long ownerId;  //发布者用户id

    private Short type;  //发布者用户类型（0：用户，1：商户）

    private String content;  //评论内容

    private Long bindShopId;  //绑定店铺id

    private Long bindTopicId;  //绑定话题id

    private Integer view;  //浏览量

    private Integer thumb;  //点赞量

    private Integer discuss;  //评论量

    private Integer turn;  //转发量

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

