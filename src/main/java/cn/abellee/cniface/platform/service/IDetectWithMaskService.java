package cn.abellee.cniface.platform.service;

import cn.abellee.cniface.platform.domain.dto.DetectWithMaskRequestDTO;
import cn.abellee.cniface.platform.domain.dto.DetectWithMaskResult;

import java.util.List;

/**
 * @author abel
 * @date 2022/8/14 4:04 AM
 */
public interface IDetectWithMaskService {

    List<DetectWithMaskResult> detect(final DetectWithMaskRequestDTO detectRequest);
}
