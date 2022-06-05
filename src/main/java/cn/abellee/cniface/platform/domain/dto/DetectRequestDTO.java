package cn.abellee.cniface.platform.domain.dto;

import lombok.Data;

/**
 * @author abel
 * @date 2022/6/5 5:36 PM
 */
@Data
public class DetectRequestDTO {

    private String faceImageBase64;

    private float score;
}
