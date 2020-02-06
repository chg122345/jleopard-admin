/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/1  14:01
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jleopard.logging.LogTypeEnum;
import org.jleopard.logging.annotation.Log;
import org.jleopard.system.entity.Dict;
import org.jleopard.system.entity.DictDetail;
import org.jleopard.system.service.DictDetailService;
import org.jleopard.web.controller.JLController;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags="字典项操作")
@RestController
@RequestMapping("/dictDetail")
public class DictDetailController extends JLController<DictDetail, String, DictDetailService> {

    @ApiOperation(value = "获取字典下所有数据", notes = "传入一个字典id，当前页，每页显示多少条")
    @GetMapping("/dict")
    @Log(value = "获取所有数据", type = LogTypeEnum.READ)
    public ResponseEntity getAll(@RequestParam(required = false) String label, @RequestParam String dict,
                                 @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        if (StringUtils.isEmpty(dict)) {
            return ResponseEntity.noContent().build();
        }
        DictDetail dictDetail = new DictDetail();
        Dict dict1 = new Dict();
        dict1.setId(dict);
        dictDetail.setDict(dict1);
        dictDetail.setLabel(label);
        Map map = this.service.findAll(dictDetail, page, size);
        return ResponseEntity.ok(map);
    }
}
