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
import org.jleopard.system.entity.Dict;
import org.jleopard.system.service.DictService;
import org.jleopard.web.controller.JLController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="字典操作")
@RestController
@RequestMapping("/dict")
public class DictController extends JLController<Dict, String, DictService> {
}
