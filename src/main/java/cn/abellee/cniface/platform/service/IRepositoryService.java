package cn.abellee.cniface.platform.service;

import cn.abellee.cniface.platform.domain.dto.*;

import java.util.List;

/**
 * @author abel
 * @date 2022/8/20 9:17 PM
 */
public interface IRepositoryService {

    void createRepository(String repoName);

    List<RepositoryResultDTO> listRepositories();

    boolean existRepository(String repoName);

    void deleteRepository(String repoName);

    void addOrUpdateItem(RepositoryAddOrUpdateItemRequestDTO repositoryAddOrUpdateItemRequestDTO);

    void deleteItem(String repoName, Long itemId);

    RepositorySearchResponseDTO search(RepositorySearchRequestDTO repositorySearchRequestDTO);

    RepositoryQueryResponseDTO query(RepositoryQueryRequestDTO repositoryQueryRequestDTO);

    byte[] getFaceImage(Long repoId, Long id);

    byte[] getSceneImage(Long repoId, Long id);
}
