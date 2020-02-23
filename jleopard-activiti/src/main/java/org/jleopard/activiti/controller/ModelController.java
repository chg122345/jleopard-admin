/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/8  20:41
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.activiti.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jleopard.activiti.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "流程模型Model操作相关", tags = {"流程模型Model操作相关"})
@RestController
@RequestMapping("model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @ApiOperation(value = "新建一个空模型")
    @PostMapping
    public ResponseEntity<?> newModel(String name, String key, String category, String description) {
        return ResponseEntity.ok(modelService.create(name, key, category, description));
    }

    @ApiOperation(value = "获取所有模型")
    @GetMapping
    public ResponseEntity<?> modelList(@RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(modelService.findAll(page, size, name));
    }

    @ApiOperation(value = "删除模型")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteModel(@PathVariable("id") String id) {
        modelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "发布模型为流程定义")
    @PostMapping("deploy/{id}")
    public ResponseEntity<?> deploy(@PathVariable("id") String id) {
        modelService.deployModel(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "上传一个已有模型")
    @PostMapping(value = "uploadFile")
    public ResponseEntity<?> deployUploadedFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(modelService.uploadModel(file));
    }

}
