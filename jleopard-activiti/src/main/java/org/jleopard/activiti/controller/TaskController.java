/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/23  11:44
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.activiti.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jleopard.activiti.service.TaskService;
import org.jleopard.activiti.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(value = "流程任务作相关", tags = {"流程任务作相关"})
@RestController
@RequestMapping("task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @ApiOperation(value = "获取任务列表", notes = "分页参数，name通过流程名称搜索")
    @GetMapping("user")
    public ResponseEntity<?> list(String name) {
        return ResponseEntity.ok(taskService.findAllByCandidateOrAssigned(name));
    }

    @ApiOperation(value = "获取当前用户任务列表", notes = "分页参数，name通过流程名称搜索")
    @GetMapping
    public ResponseEntity<?> currentUserList(@RequestParam(required = false) String name, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(taskService.findCurrentUserAllTask(name, Pagination.of(page, size)));
    }

    @ApiOperation(value = "执行任务", notes = "传任务id，局部变量参数")
    @PostMapping("{id}")
    public ResponseEntity<?> complete(@PathVariable String id, @RequestBody Map<String,Object> variables) {
        taskService.complete(id, variables);
        return ResponseEntity.noContent().build();
    }



}
