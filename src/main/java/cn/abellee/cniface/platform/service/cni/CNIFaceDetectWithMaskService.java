package cn.abellee.cniface.platform.service.cni;

import cn.abellee.cniface.grpc.*;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * @author abel
 * @date 2022/8/14 3:44 AM
 */
@Service
@Slf4j
public class CNIFaceDetectWithMaskService {

    @GrpcClient("cniface")
    private DetectWithMaskServiceGrpc.DetectWithMaskServiceBlockingStub detectWithMaskServiceBlockingStub;

    public DetectWithMaskResponse detect(final DetectWithMaskRequest detectWithMaskRequest) throws StatusRuntimeException {
        return detectWithMaskServiceBlockingStub.detect(detectWithMaskRequest);
    }
}
