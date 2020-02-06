/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/1/31  16:04
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.controller;

import io.swagger.annotations.Api;
import org.jleopard.system.entity.Role;
import org.jleopard.system.service.RoleService;
import org.jleopard.web.controller.JLController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="用户角色操作")
@RestController
@RequestMapping("/role")
public class RoleController extends JLController<Role,String, RoleService> {
}