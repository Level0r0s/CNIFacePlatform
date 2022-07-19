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
    CNIFACE_GRPC_ERROR(-500, "CNIFace grpc 链接错误"),
    CNIFACE_GRPC_RETURN_ERROR(-100, "CNIFace grpc 返回值不为0"),
    VALID_ERROR(-22000, "请求参数不符合规范"),
    ;


    private final int code;

    private final String description;
}
