/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/9  17:33
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.activiti.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.history.HistoricActivityInstance;
import org.jleopard.activiti.dto.ProcessDto;
import org.jleopard.activiti.service.ProcessService;
import org.jleopard.resource.annotation.JLAnonymousAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(value = "流程操作相关", tags = {"流程操作相关"})
@RestController
@RequestMapping("process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    /**
     * 流程列表
     */
    @ApiOperation(value = "获取流程列表", notes = "分页参数，name通过流程名称搜索")
    @GetMapping
    public ResponseEntity<?> list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String name) {
        return ResponseEntity.ok(processService.findAll(page, size, name));
    }


    @ApiOperation(value = "删除流程", notes = "通过流程部署id删除一个流程")
    @DeleteMapping("{deployId}")
    public ResponseEntity<?> delete(@PathVariable String deployId) {
        processService.deleteByDeploymentId(deployId);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "激活/挂起流程",notes = "通过流程定义id激活或者挂起流程")
    @PutMapping("{id}")
    public ResponseEntity<?> suspend(@PathVariable String id, @RequestParam Boolean suspend) {
        return ResponseEntity.ok(processService.activateOrSuspendById(id, suspend));
    }


    /**
     * 启动一个新的流程
     */
    @ApiOperation(value = "启动一个新的流程",notes = "传入流程定义id/key，传入流程变量")
    @PostMapping("start")
    public ResponseEntity<?> startProcesses(@RequestBody ProcessDto processDto) {
        processService.start(processDto);
        return ResponseEntity.noContent().build();
    }

    /**
     * 生成流程图
     **/
    @ApiOperation(value = "生成流程图",notes = "传入流程定义id")
    @GetMapping("img/{id}")
    @JLAnonymousAccess
    public void getProcessImg(@PathVariable(value = "id") String id, HttpServletResponse response) throws IOException {
        processService.generateProcessImgById(id, response);
    }


    @ApiOperation(value = "生成运行中流程图",notes = "传入流程实例id")
    @GetMapping("/img/{processInstanceId}/instance")
    @JLAnonymousAccess
    public void generateProcessImg(@PathVariable(value = "processInstanceId") String processInstanceId, HttpServletResponse response) throws IOException {
        processService.generateProcessImgByInstanceId(processInstanceId, response);
    }


    @ApiOperation(value = "获取历史流程",notes = "传入流程实例id")
    @GetMapping("/{processInstanceId}")
    public ResponseEntity<List<HistoricActivityInstance>> generateProcessImg(@PathVariable(value = "processInstanceId") String processInstanceId) {
        return ResponseEntity.ok(processService.getHistoricActivityInstance(processInstanceId));
    }

}
