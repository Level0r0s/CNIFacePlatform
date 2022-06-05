package cn.abellee.cniface.platform.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author abel
 * @date 2022/6/5 5:43 PM
 */
@Getter
@AllArgsConstructor
public enum CNIFaceResponseCode {

    OK(0, "OK"),
    VALID_ERROR(-22000, "请求参数不符合规范"),
    ;


    private final int code;

    private final String description;
}
