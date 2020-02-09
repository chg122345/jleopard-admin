/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/8  20:48
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.activiti.controller;

import org.jleopard.resource.annotation.JLAnonymousAccess;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @JLAnonymousAccess
    @GetMapping(value = {"editor", "/"})
    public String editor() {
        return "modeler";
    }
}
