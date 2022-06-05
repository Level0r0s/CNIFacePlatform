package cn.abellee.cniface.platform.service.impl;

import cn.abellee.cniface.grpc.DetectRequest;
import cn.abellee.cniface.grpc.DetectResponse;
import cn.abellee.cniface.grpc.DetectServiceGrpc;
import cn.abellee.cniface.platform.domain.dto.DetectRequestDTO;
import cn.abellee.cniface.platform.domain.dto.DetectResult;
import cn.abellee.cniface.platform.service.IDetectService;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author abel
 * @date 2022/6/5 3:28 PM
 */
@Service
@Slf4j
public class DetectServiceImpl implements IDetectService {

    @GrpcClient("cniface")
    private DetectServiceGrpc.DetectServiceBlockingStub detectServiceStub;

    @Override
    public List<DetectResult> detect(final DetectRequestDTO detectRequest) {
        try {
            DetectResponse detectResponse = detectServiceStub.detect(
                    DetectRequest.newBuilder()
                            .setFaceImageBase64(detectRequest.getFaceImageBase64())
                            .setScore(detectRequest.getScore())
                            .build()
            );
            // TO DO detectResponse.getCode()
            List<DetectResult> detectResults = new ArrayList<>();
            for (cn.abellee.cniface.grpc.DetectResult grpcDetectResult : detectResponse.getResultsList()) {
                DetectResult detectResult = new DetectResult(grpcDetectResult);
                detectResults.add(detectResult);
            }
            return detectResults;
        } catch (final StatusRuntimeException e) {
            log.error("FAILED with " + e.getStatus().getCode().name());
            e.printStackTrace();
        }
        return null;
    }

}
