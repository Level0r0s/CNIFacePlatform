package cn.abellee.cniface.platform.service;

import cn.abellee.cniface.platform.domain.dto.ExtractFeatureRequestDTO;
import cn.abellee.cniface.platform.domain.dto.ExtractFeatureResponseDTO;
import cn.abellee.cniface.platform.domain.dto.SimilarityRequestDTO;
import cn.abellee.cniface.platform.domain.dto.SimilarityResponseDTO;

/**
 * @author abel
 * @date 2022/6/6 7:39 PM
 */
public interface IRecognitionService {

    ExtractFeatureResponseDTO extractFeature(ExtractFeatureRequestDTO recognitionRequest);

    SimilarityResponseDTO similarity(SimilarityRequestDTO similarityRequest);
}
