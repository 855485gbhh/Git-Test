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
@Table("qnc_shop")
public class Shop {

    @Id
    @NotNull(groups = {Entity.Create.class, Entity.Update.class}, message = "id不可为空")
    @DecimalMin(value = "400000000000000", inclusive = true, groups = {Entity.Create.class, Entity.Update.class}, message = "id不可为空")
    private Long id;  //主键id，唯一标识

    private String name;  //店铺全称

    private String branchName;  //分店名称

    private String markerName;  //地图上的店铺名称

    private Short type;  //店铺名称（0：固定店铺，1：移动店铺）

    private Long categoryId;  //所属类目id

    private Long ownerId;  //所有者用户id

    private String avatar;  //店铺头图

    private Short isShowMarker;  //是否展示在地图上

    private BigDecimal latitude;  //纬度

    private BigDecimal longitude;  //经度

    private String provinceId;  //省级行政单位

    private String cityId;  //市级行政单位

    private String districtId;  //县级行政单位

    private String tradingAreaId;  //商圈id

    private Short weight;  //权重值，最高255

    private String servicePhone1;  //服务电话1

    private String servicePhone2;  //服务电话2

    private String address;  //具体地址，排除省市区后的

    private Short isAuth;  //是否已经通过认证审核(0:未通过，1：已通过)，默认为0

    private String imageList;  //店铺图片

    private Short businessType;  //经营状态

    private Short businessOpen;  //每日经营开始时间

    private Short businessClose;  //每日经营结束时间
    @NotNull(groups = {Entity.Create.class}, message = "创建时间错误")
    @DecimalMin(value = "1600000000", inclusive = true, groups = {Entity.Create.class}, message = "创建时间错误")
    private Timestamp gmtCreate;  //数据创建时间
    @NotNull(groups = {Entity.Create.class, Entity.Update.class}, message = "更新时间错误")
    @DecimalMin(value = "1600000000", inclusive = true, groups = {Entity.Create.class, Entity.Update.class}, message = "更新时间错误")
    private Timestamp gmtModified;  //上一次更新数据时间
    @Range(min = 0, max = 1, groups = {Entity.Update.class}, message = "逻辑删除字段错误")
    private Short isDeleted;  //是否已删除（0：未删除，1：已删除)，默认为0





}

