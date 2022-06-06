package cn.abellee.cniface.platform.service.impl;

import cn.abellee.cniface.grpc.*;
import cn.abellee.cniface.platform.domain.dto.ExtractFeatureRequestDTO;
import cn.abellee.cniface.platform.domain.dto.ExtractFeatureResponseDTO;
import cn.abellee.cniface.platform.domain.dto.SimilarityRequestDTO;
import cn.abellee.cniface.platform.domain.dto.SimilarityResponseDTO;
import cn.abellee.cniface.platform.service.IRecognitionService;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * @author abel
 * @date 2022/6/6 7:42 PM
 */
@Service
@Slf4j
public class RecognitionServiceImpl implements IRecognitionService {

    @GrpcClient("cniface")
    private RecognitionServiceGrpc.RecognitionServiceBlockingStub recognitionServiceBlockingStub;

    @Override
    public ExtractFeatureResponseDTO extractFeature(ExtractFeatureRequestDTO extractFeatureRequestDTO) {
        try {
            ExtractFeatureRequest extractFeatureRequest = ExtractFeatureRequest.newBuilder()
                    .addAllKps(extractFeatureRequestDTO.getKps())
                    .setFaceImageBase64(extractFeatureRequestDTO.getFaceImageBase64())
                    .build();
            ExtractFeatureResponse extractFeatureResponse = recognitionServiceBlockingStub.extractFeature(extractFeatureRequest);
            // TO DO extractFeatureResponse.getCode()
            ExtractFeatureResponseDTO extractFeatureResponseDTO = new ExtractFeatureResponseDTO();
            extractFeatureResponseDTO.setFeatureBase64(extractFeatureResponse.getFeatureBase64());

            return extractFeatureResponseDTO;
        } catch (final StatusRuntimeException e) {
            log.error("FAILED with " + e.getStatus().getCode().name());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SimilarityResponseDTO similarity(SimilarityRequestDTO similarityRequestDTO) {
        try {
            SimilarityRequest similarityRequest = SimilarityRequest.newBuilder()
                    .setFeatureBase641(similarityRequestDTO.getFeatureBase64_1())
                    .setFeatureBase642(similarityRequestDTO.getFeatureBase64_2())
                    .build();
            SimilarityeResponse similarityeResponse = recognitionServiceBlockingStub.similarity(similarityRequest);
            // TO DO response.getCode()
            float similarity = similarityeResponse.getSimilarity();
            similarity = similarity < 0 ? 0 : similarity;
            similarity = similarity > 1 ? 1 : similarity;
            similarity = similarity * 100;

            SimilarityResponseDTO similarityResponseDTO = new SimilarityResponseDTO();
            similarityResponseDTO.setSimilarity(similarity);
            return similarityResponseDTO;
        } catch (final StatusRuntimeException e) {
            log.error("FAILED with " + e.getStatus().getCode().name());
            e.printStackTrace();
        }
        return null;
    }
}
