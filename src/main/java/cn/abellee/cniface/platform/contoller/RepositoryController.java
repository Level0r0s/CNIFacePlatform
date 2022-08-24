package cn.abellee.cniface.platform.contoller;

import cn.abellee.cniface.platform.domain.common.CNIFaceResponse;
import cn.abellee.cniface.platform.domain.dto.*;
import cn.abellee.cniface.platform.service.IRepositoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author abel
 * @date 2022/8/20 9:59 PM
 */
@RestController
@RequestMapping("/repository")
public class RepositoryController {

    private final IRepositoryService repositoryService;

    public RepositoryController(IRepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @PostMapping("/create")
    public CNIFaceResponse<?> create(@RequestBody CreateRepositoryDTO createRepositoryDTO){
        repositoryService.createRepository(createRepositoryDTO.getName());
        return CNIFaceResponse.ok();
    }

    @DeleteMapping("/delete/{name}")
    public CNIFaceResponse<?> delete(@PathVariable String name){
        repositoryService.deleteRepository(name);
        return CNIFaceResponse.ok();
    }

    @GetMapping("/list")
    public CNIFaceResponse<List<RepositoryResultDTO>> list(){
        List<RepositoryResultDTO> repositoryResultDTOS = repositoryService.listRepositories();
        return CNIFaceResponse.ok(repositoryResultDTOS);
    }
}
