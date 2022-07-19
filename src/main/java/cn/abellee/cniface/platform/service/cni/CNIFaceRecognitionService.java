package cn.abellee.cniface.platform.service.cni;

import cn.abellee.cniface.grpc.*;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * @author abel
 * @date 2022/6/6 10:09 PM
 */
@Service
@Slf4j
public class CNIFaceRecognitionService {

    @GrpcClient("cniface")
    private RecognitionServiceGrpc.RecognitionServiceBlockingStub recognitionServiceBlockingStub;

    public ExtractFeatureResponse extractFeature(ExtractFeatureRequest extractFeatureRequest) throws StatusRuntimeException {
            return recognitionServiceBlockingStub.extractFeature(extractFeatureRequest);
    }

    public SimilarityResponse similarity(SimilarityRequest similarityRequest) throws StatusRuntimeException {
        return recognitionServiceBlockingStub.similarity(similarityRequest);
    }
}
