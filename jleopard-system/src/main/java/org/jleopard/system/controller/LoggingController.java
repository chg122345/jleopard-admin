/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/3  15:59
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jleopard.system.entity.Logging;
import org.jleopard.system.dto.LoggingQueryDto;
import org.jleopard.system.service.LoggingService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Api(tags="日志操作")
@RestController
@RequestMapping("/log")
public class LoggingController {

    private final LoggingService service;

    public LoggingController(LoggingService service) {
        this.service = service;
    }

    @ApiOperation(value = "删除所有日志", notes = "传入日志类型")
    @DeleteMapping("/deleteAll")
    public ResponseEntity deleteAll(@RequestParam(required = false) Integer type) {
        service.deleteAllByType(type);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "删除日志数据接口", notes = "传入ID")
    @DeleteMapping({"/{id}"})
    public ResponseEntity deleteById(@PathVariable String id) {
        this.service.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "批量删除日志数据接口", notes = "传入一个ID集合")
    @DeleteMapping("/delBach")
    public ResponseEntity deleteBachByIds(@RequestParam Set<String> ids) {
        this.service.deleteBatchByIds(ids);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "获取所有日志", notes = "获取所有日志")
    @GetMapping
    public ResponseEntity<Page<Logging>> getAll(LoggingQueryDto logging,
                                                @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Logging> map = this.service.queryAll(logging, page, size);
        return ResponseEntity.ok(map);
    }
}
