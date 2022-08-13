package cn.abellee.cniface.platform.contoller;

import cn.abellee.cniface.platform.domain.common.CNIFacePagedResponse;
import cn.abellee.cniface.platform.domain.dto.DetectWithMaskRequestDTO;
import cn.abellee.cniface.platform.domain.dto.DetectWithMaskResult;
import cn.abellee.cniface.platform.service.IDetectWithMaskService;
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
@RequestMapping("/detect_with_mask")
public class DetectWithMaskController {

    private final IDetectWithMaskService detectService;

    public DetectWithMaskController(IDetectWithMaskService detectService) {
        this.detectService = detectService;
    }

    @PostMapping("/face")
    public CNIFacePagedResponse<DetectWithMaskResult> detect(@RequestBody DetectWithMaskRequestDTO detectRequest){
        List<DetectWithMaskResult> detectResults = detectService.detect(detectRequest);
        return CNIFacePagedResponse.ok(detectResults.size(), detectResults);
    }


}
