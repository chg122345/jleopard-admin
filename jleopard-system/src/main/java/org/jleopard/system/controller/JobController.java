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
import org.jleopard.system.entity.Job;
import org.jleopard.system.service.JobService;
import org.jleopard.web.controller.JLController;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="职位操作")
@RestController
@RequestMapping("/job")
public class JobController extends JLController<Job, String, JobService> {


    @GetMapping("/select")
    public ResponseEntity<Page<Job>> jobSelect(@RequestParam(value = "codeOrName", required = false) String codeOrName,
                                         @RequestParam(value = "deptId", required = false) String deptId,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Job> jobPage = this.service.findByCodeOrName(codeOrName, deptId, page, size);
        return ResponseEntity.ok(jobPage);
    }
}
