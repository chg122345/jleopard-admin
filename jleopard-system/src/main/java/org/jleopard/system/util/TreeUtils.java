/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/2  14:54
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.util;

import org.jleopard.system.entity.Menu;
import org.jleopard.system.vo.MenuVo;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class TreeUtils {

    /**
     * 构建菜单树
     * @param menuVoSet  父级目录
     * @param allMenus 所有子集
     */
    public static void findChildren(Set<MenuVo> menuVoSet, Set<Menu> allMenus) {
        for (MenuVo menuVo : menuVoSet) {
            if (StringUtils.hasText(menuVo.getId())) {
                Set<MenuVo> children = allMenus.stream().filter(i -> menuVo.getId().equals(i.getParentId()))
                        .map(i -> new MenuVo(i.getId(), i.getSortNumber(), i.getPath(), i.getPath(), "", i.getIsHidden(), i.getComponent(), false,
                                new MenuVo.MenuMeta(i.getName(), i.getIcon()), new TreeSet<>(Comparator.comparing(MenuVo::getId)))).collect(Collectors.toCollection(TreeSet::new));
                if (!CollectionUtils.isEmpty(children)) {
                    findChildren(children, allMenus);
                    menuVo.setChildren(children);
                }
            }
        }
    }

    public static boolean exists(final String id, Set<MenuVo> menuVoSet) {
        return menuVoSet.stream().anyMatch(i -> i.getId().equals(id));
    }
}
