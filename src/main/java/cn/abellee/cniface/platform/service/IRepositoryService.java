package cn.abellee.cniface.platform.service;

import cn.abellee.cniface.platform.domain.dto.RepositoryResultDTO;

import java.util.List;

/**
 * @author abel
 * @date 2022/8/20 9:17 PM
 */
public interface IRepositoryService {

    void createRepository(String name);

    List<RepositoryResultDTO> listRepositories();

    void deleteRepository(String name);
}
