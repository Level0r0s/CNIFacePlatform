package cn.abellee.cniface.platform.exception;

import cn.abellee.cniface.platform.domain.common.CNIFaceResponseCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author abel
 * @date 2022/6/5 5:45 PM
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class CNIFaceException extends RuntimeException {

    private CNIFaceResponseCode cniFaceResponseCode;

    public CNIFaceException(CNIFaceResponseCode cniFaceResponseCode, String message) {
        super(message);
        this.cniFaceResponseCode = cniFaceResponseCode;
    }

    public CNIFaceResponseCode getCNIFaceResponseCode() {
        return cniFaceResponseCode;
    }
}