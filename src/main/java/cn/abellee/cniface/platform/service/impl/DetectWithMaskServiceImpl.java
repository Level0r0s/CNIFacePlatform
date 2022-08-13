package cn.abellee.cniface.platform.service.impl;

import cn.abellee.cniface.grpc.*;
import cn.abellee.cniface.platform.domain.dto.DetectWithMaskRequestDTO;
import cn.abellee.cniface.platform.domain.dto.DetectWithMaskResult;
import cn.abellee.cniface.platform.exception.CNIFaceException;
import cn.abellee.cniface.platform.service.IDetectWithMaskService;
import cn.abellee.cniface.platform.service.cni.CNIFaceDetectWithMaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author abel
 * @date 2022/6/5 3:28 PM
 */
@Service
@Slf4j
public class DetectWithMaskServiceImpl implements IDetectWithMaskService {

    private final CNIFaceDetectWithMaskService cniFaceDetectWithMaskService;

    public DetectWithMaskServiceImpl(CNIFaceDetectWithMaskService cniFaceDetectWithMaskService) {
        this.cniFaceDetectWithMaskService = cniFaceDetectWithMaskService;
    }

    @Override
    public List<DetectWithMaskResult> detect(final DetectWithMaskRequestDTO detectRequest) {
        DetectWithMaskResponse detectResponse = cniFaceDetectWithMaskService.detect(
                DetectWithMaskRequest.newBuilder()
                        .setFaceImageBase64(detectRequest.getFaceImageBase64())
                        .setScore(detectRequest.getScore())
                        .build()
        );


        if (detectResponse.getCode() != 0) throw new CNIFaceException((int)detectResponse.getCode(), detectResponse.getMessage());
        List<DetectWithMaskResult> detectResults = new ArrayList<>();
        for (cn.abellee.cniface.grpc.DetectWithMaskResult grpcDetectResult : detectResponse.getResultsList()) {
            DetectWithMaskResult detectWithMaskResult = new DetectWithMaskResult(grpcDetectResult);
            detectResults.add(detectWithMaskResult);
        }
        return detectResults;
    }

}
