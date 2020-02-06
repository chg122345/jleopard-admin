/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/1/21  15:44
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
import org.jleopard.resource.annotation.JLAnonymousAccess;
import org.jleopard.system.entity.User;
import org.jleopard.system.service.UserService;
import org.jleopard.system.util.SecurityContextUtils;
import org.jleopard.system.vo.UserAuthVo;
import org.jleopard.system.vo.UserVo;
import org.jleopard.web.controller.JLController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags="用户操作")
@RestController
@RequestMapping("/user")
public class UserController extends JLController<User, String, UserService> {

    @GetMapping("/name")
    @ApiOperation(value="获取用户信息", notes="通过名称获取用户信息")
    public ResponseEntity<User> findByUsername(String name) {
        User byUsername = service.findByUsername(name);
        return ResponseEntity.ok(byUsername);
    }

    @JLAnonymousAccess
    @GetMapping("/auth")
    public UserAuthVo authUser(String account) {
        return service.findByAccount(account);
    }

    @GetMapping("/info")
    @ApiOperation(value="获取用户信息", notes="通过名称获取用户信息")
    public ResponseEntity<UserVo> userInfo() {
        Map<String, ?> map = SecurityContextUtils.getPrincipal();
        UserVo userInfo = service.findUserInfo((String) map.get("id"));
        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/reset")
    @Log(value = "重置密码", type = LogTypeEnum.WRITE)
    @ApiOperation(value="重置密码", notes="通过id重置密码")
    public ResponseEntity<?> resetPwd(String id, String password) {
        int i = service.resetPassword(id, password);
        if (i > 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.unprocessableEntity().build();
    }
}
