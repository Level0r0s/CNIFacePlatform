package cn.abellee.cniface.platform.service.impl;

import cn.abellee.cniface.platform.domain.dto.RepositoryResultDTO;
import cn.abellee.cniface.platform.milvus.MilvusClientService;
import cn.abellee.cniface.platform.service.IRepositoryService;
import io.milvus.grpc.ShowCollectionsResponse;
import io.milvus.param.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author abel
 * @date 2022/8/20 9:35 PM
 */
@Service
@Slf4j
public class RepositoryService implements IRepositoryService {

    private final MilvusClientService milvusClientService;

    public RepositoryService(MilvusClientService milvusClientService) {
        this.milvusClientService = milvusClientService;
    }

    @Override
    public void createRepository(String name) {
        milvusClientService.createFaceCollection(name);
        milvusClientService.createIndex(name);
    }

    @Override
    public void deleteRepository(String name) {
        milvusClientService.dropCollection(name);
    }

    @Override
    public List<RepositoryResultDTO> listRepositories() {
        List<RepositoryResultDTO> repositoryResultDTOS = new ArrayList<>();
        R<ShowCollectionsResponse> showCollectionsResponseR = milvusClientService.showCollections();
        ShowCollectionsResponse showCollectionsResponse = showCollectionsResponseR.getData();
        for (int i = 0; i < showCollectionsResponse.getCollectionIdsCount(); i++) {
            RepositoryResultDTO repositoryResultDTO = new RepositoryResultDTO();
            repositoryResultDTO.setId(showCollectionsResponse.getCollectionIds(i));
            repositoryResultDTO.setName(showCollectionsResponse.getCollectionNames(i));
//            repositoryResultDTO.setTotal(showCollectionsResponse.getInMemoryPercentages(i));
            repositoryResultDTO.setCreateTime(showCollectionsResponse.getCreatedTimestamps(i));
            repositoryResultDTOS.add(repositoryResultDTO);
        }
        return repositoryResultDTOS;
    }

}
