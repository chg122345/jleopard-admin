/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/4  13:03
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.controller;

import org.jleopard.common.model.ReflectionModel;
import org.jleopard.common.util.ReflectionUtils;
import org.jleopard.resource.annotation.JLAnonymousAccess;
import org.jleopard.web.exception.BadRequestException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    private final ApplicationContext applicationContext;

    public TaskController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @JLAnonymousAccess
    @PostMapping("/execJob")
    public ResponseEntity execJob(@RequestBody ReflectionModel model){
        Object bean = applicationContext.getBean(model.getBeanName());
        model.setBean(bean);
        if (StringUtils.hasText(model.getParams())) {
            Object[] methodParams = model.getParams().split(",");
            model.setMethodParams(methodParams);
        }
        try {
            ReflectionUtils.invoke(model);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }
}
