package cn.abellee.cniface.platform;

import cn.abellee.cniface.platform.milvus.MilvusClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author abel
 * @date 2022/8/14 3:04 PM
 */
public class MilvusTests extends CNIFacePlatformApplicationTests {

    @Autowired
    private MilvusClientService milvusClientService;

    @Test
    void testCreateCollection() {
        String repoName = "TEST_REPO";
        milvusClientService.createFaceCollection(repoName);
        milvusClientService.showCollections();
        milvusClientService.describeCollection(repoName);
        milvusClientService.hasCollection(repoName);
        milvusClientService.getCollectionStatistics(repoName);
    }
}
