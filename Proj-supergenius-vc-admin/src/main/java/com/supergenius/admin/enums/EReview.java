package com.supergenius.admin.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 审核状态
 *
 * @author jiashitao
 * @date 20190928
 */
@Getter
public enum EReview{
    unreviewed(0),//未审查
    reviewed(1),//已审查
    underreview(2),//审查中
    success(3),//通过
    fail(4);//未通过

    @EnumValue
    private Integer value;

    private EReview(Integer value) {
        this.value = value;
    }

}
