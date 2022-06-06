package cn.abellee.cniface.platform.domain.dto;

import lombok.Data;

/**
 * @author abel
 * @date 2022/6/6 7:57 PM
 */
@Data
public class SimilarityRequestDTO {

    private String featureBase64_1;

    private String featureBase64_2;
}
