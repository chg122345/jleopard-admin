/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/1/31  15:56
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.service;

import org.jleopard.common.base.JLService;
import org.jleopard.system.entity.Menu;

import java.util.List;

public interface MenuService extends JLService<Menu, String> {

    List<Menu> findAllByType(List<Integer> type);
}
