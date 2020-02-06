/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/3  21:01
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.monitor.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jleopard.logging.LogTypeEnum;
import org.jleopard.logging.annotation.Log;
import org.jleopard.monitor.entity.QuartzJob;
import org.jleopard.monitor.service.QuartzJobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@Api(tags="定时任务操作")
@RestController
@RequestMapping("/task")
public class QuartzJobController {

    private final QuartzJobService service;

    public QuartzJobController(QuartzJobService service) {
        this.service = service;
    }

    @ApiOperation(value = "创建定时任务", notes = "传入一个实体")
    @PostMapping
    @Log(value = "创建定时任务", type = LogTypeEnum.WRITE)
    public ResponseEntity<QuartzJob> add(@RequestBody QuartzJob entity) {
        QuartzJob result = this.service.create(entity);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "批量删除定时任务", notes = "传入一个ID集合")
    @DeleteMapping({"/delBach"})
    @Log(value = "删除定时任务", type = LogTypeEnum.DELETE)
    public ResponseEntity deleteBachByIds(@RequestParam Set<String> ids) {
        this.service.deleteJobByIds(ids);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "更新数据接口", notes = "传入一个实体，要更新的ID")
    @PutMapping({"/{id}"})
    @Log(value = "更新数据(部分字段)", type = LogTypeEnum.WRITE)
    public ResponseEntity<QuartzJob> updateStatus(@PathVariable String id) {
        QuartzJob result = this.service.updateIsPause(id);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "更新定时任务", notes = "传入一个实体（包含ID）")
    @PatchMapping
    @Log(value = "更新定时任务", type = LogTypeEnum.WRITE)
    public ResponseEntity<QuartzJob> update(@RequestBody QuartzJob entity) {
        QuartzJob result = this.service.updateJob(entity);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "获取所有定时任务", notes = "传入一个实体，当前页，每页显示多少条")
    @GetMapping
    @Log(value = "获取所有定时任务", type = LogTypeEnum.READ)
    public ResponseEntity getAll(QuartzJob entity, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Map map = this.service.findAll(entity, page, size);
        return ResponseEntity.ok(map);
    }


    @Log("执行定时任务")
    @ApiOperation("执行定时任务")
    @PutMapping(value = "/exec/{id}")
    public ResponseEntity<Object> execution(@PathVariable String id){
        service.execution(id);
        return ResponseEntity.noContent().build();
    }

}
