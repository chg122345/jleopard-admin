/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/1/28  20:13
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.controller;

import io.swagger.annotations.Api;
import org.jleopard.system.entity.Dept;
import org.jleopard.system.service.DeptService;
import org.jleopard.web.controller.JLController;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags="组织操作")
@RestController
@RequestMapping("/dept")
public class DeptController extends JLController<Dept, String, DeptService> {

    @GetMapping("/select")
    public ResponseEntity<Page<Dept>> deptSelect(@RequestParam(value = "codeOrName", required = false) String codeOrName,
    @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Dept> deptPage = this.service.findByCodeOrName(codeOrName, page, size);
        return ResponseEntity.ok(deptPage);
    }

    @GetMapping("/treeSelect")
    public ResponseEntity<List<Dept>> deptBuildTree(@RequestParam(value = "codeOrName", required = false) String codeOrName) {
        List<Dept> deptPage = this.service.findByCodeOrNameToTree(codeOrName);
        return ResponseEntity.ok(deptPage);
    }
}
