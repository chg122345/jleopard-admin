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
import io.swagger.annotations.ApiOperation;
import org.jleopard.logging.LogTypeEnum;
import org.jleopard.logging.annotation.Log;
import org.jleopard.system.entity.Role;
import org.jleopard.system.entity.User;
import org.jleopard.system.service.RoleService;
import org.jleopard.system.service.UserService;
import org.jleopard.web.controller.JLController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags="用户角色操作")
@RestController
@RequestMapping("/role")
public class RoleController extends JLController<Role,String, RoleService> {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @ApiOperation(value = "获取该角色下所有用户", notes = "传入一个角色id")
    @Log(value = "获取该角色下所有用户", type = LogTypeEnum.READ)
    public ResponseEntity<Page<User>> roleUsers(String roleId, @RequestParam(required = false) String name,
                                                @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.findByRoleId(roleId, name, PageRequest.of(page, size)));
    }
}