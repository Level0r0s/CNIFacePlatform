package cn.abellee.cniface.platform.service.cni;

import cn.abellee.cniface.grpc.DetectRequest;
import cn.abellee.cniface.grpc.DetectResponse;
import cn.abellee.cniface.grpc.DetectServiceGrpc;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * @author abel
 * @date 2022/6/5 3:27 PM
 */
@Service
@Slf4j
public class CNIFaceDetectService {

    @GrpcClient("cniface")
    private DetectServiceGrpc.DetectServiceBlockingStub detectServiceStub;

    public DetectResponse detect(final DetectRequest detectRequest) throws StatusRuntimeException {
        return detectServiceStub.detect(detectRequest);
    }
}
