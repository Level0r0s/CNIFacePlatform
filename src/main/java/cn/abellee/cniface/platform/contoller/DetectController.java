package cn.abellee.cniface.platform.contoller;

import cn.abellee.cniface.grpc.DetectResponse;
import cn.abellee.cniface.platform.domain.common.CNIFacePagedResponse;
import cn.abellee.cniface.platform.domain.dto.DetectRequestDTO;
import cn.abellee.cniface.platform.domain.dto.DetectResponseDTO;
import cn.abellee.cniface.platform.domain.dto.DetectResult;
import cn.abellee.cniface.platform.service.IDetectService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author abel
 * @date 2022/6/5 3:21 PM
 */
@RestController
@RequestMapping("/detect")
public class DetectController {

    private final IDetectService detectService;

    public DetectController(IDetectService detectService) {
        this.detectService = detectService;
    }

    @PostMapping("/face")
    public CNIFacePagedResponse<DetectResult> detect(@RequestBody DetectRequestDTO detectRequest){
        List<DetectResult> detectResults = detectService.detect(detectRequest);
        return CNIFacePagedResponse.ok(detectResults.size(), detectResults);
    }


}
