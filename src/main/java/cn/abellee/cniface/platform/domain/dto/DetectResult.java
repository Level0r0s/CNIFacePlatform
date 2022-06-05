package cn.abellee.cniface.platform.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author abel
 * @date 2022/6/5 5:38 PM
 */
@Data
public class DetectResult {

    public DetectResult(cn.abellee.cniface.grpc.DetectResult detectResult) {
        this.score = detectResult.getScore();
        this.x = detectResult.getX();
        this.y = detectResult.getY();
        this.w = detectResult.getW();
        this.h = detectResult.getH();
        this.kps = detectResult.getKpsList();
    }

    private float score;

    private float x;

    private float y;

    private float w;

    private float h;

    private List<Float> kps;
}
