/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/1/31  16:02
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.controller;

import io.swagger.annotations.Api;
import org.jleopard.logging.LogTypeEnum;
import org.jleopard.logging.annotation.Log;
import org.jleopard.system.entity.Menu;
import org.jleopard.system.service.MenuService;
import org.jleopard.web.controller.JLController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags="菜单权限操作")
@RestController
@RequestMapping("/menu")
public class MenuController extends JLController<Menu,String, MenuService> {

    @GetMapping("/select")
    public ResponseEntity<List<Menu>> selectAll(@RequestParam(value = "type", required = false) List<Integer> type) {
        List<Menu> list = this.service.findAllByType(type);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Menu>> getAll(@RequestParam(value = "type", required = false) List<Integer> type) {
        List<Menu> list = this.service.findAllByType(type);
        return ResponseEntity.ok(list);
    }

}
