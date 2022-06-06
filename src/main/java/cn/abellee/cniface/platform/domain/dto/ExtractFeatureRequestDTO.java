package cn.abellee.cniface.platform.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * @author abel
 * @date 2022/6/6 7:39 PM
 */
@Data
public class ExtractFeatureRequestDTO {

    private String faceImageBase64;

    private List<Float> kps;

}
