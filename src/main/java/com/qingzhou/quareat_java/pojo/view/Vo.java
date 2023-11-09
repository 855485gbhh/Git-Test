package com.qingzhou.quareat_java.pojo.view;

import lombok.Data;

@Data
public class Vo {
    public interface UserBaseVo {
    }

    public interface MerchantBaseVo extends UserBaseVo {

    }

    public interface AdminBaseVo extends MerchantBaseVo {

    }
}
