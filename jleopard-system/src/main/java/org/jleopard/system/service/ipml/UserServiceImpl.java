/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/1/21  15:42
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.service.ipml;

import org.jleopard.data.base.JLServiceImpl;
import org.jleopard.system.entity.Menu;
import org.jleopard.system.entity.Role;
import org.jleopard.system.entity.User;
import org.jleopard.system.repository.UserRepository;
import org.jleopard.system.service.UserService;
import org.jleopard.system.util.TreeUtils;
import org.jleopard.system.vo.MenuVo;
import org.jleopard.system.vo.UserAuthVo;
import org.jleopard.system.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@CacheConfig(cacheNames = "user")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends JLServiceImpl<User,String, UserRepository> implements UserService {

    @Cacheable(key = "#p0")
    @Override
    public User findByUsername(String name) {
        long l = System.currentTimeMillis();
        User byNameIs = repository.findByNameIs(name);
        System.err.println(System.currentTimeMillis() - l);
        return byNameIs;
    }

    @Override
    public UserAuthVo findByAccount(String account) {
        User user = repository.findOneByAccount(account);
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        UserAuthVo userAuthVo = new UserAuthVo();
        BeanUtils.copyProperties(user,userAuthVo);
        Set<Role> roles = user.getRoles();
        if (!CollectionUtils.isEmpty(roles)) {
            final Set<String> perms = new HashSet<>();
            roles.forEach(i -> {
                perms.add(i.getCode());
                perms.addAll(i.getMenus().stream().map(Menu::getPerms).collect(Collectors.toSet()));
            });
            userAuthVo.setPerms(perms);
        }
        return userAuthVo;
    }

    @Override
    public UserVo findUserInfo(String id) {
        Optional<User> optionalUser = findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);
            Set<Role> roles = user.getRoles();
            if (!CollectionUtils.isEmpty(roles)) {
                final Set<String> perms = new HashSet<>();
                final Set<MenuVo> routes = new TreeSet<>(Comparator.comparing(MenuVo::getId));
                roles.forEach(i -> {
                    perms.add(i.getCode());
                    Set<Menu> menus = i.getMenus();
                    if (!CollectionUtils.isEmpty(menus)) {
                        Set<String> permsCodes = menus.stream().filter(m -> (2 == m.getType() && StringUtils.hasText(m.getPerms()))).map(Menu::getPerms).collect(Collectors.toSet());
                        perms.addAll(permsCodes);
                        // 提取是目录/菜单父级
                        Set<MenuVo> parent = menus.stream().filter(m -> (m.getType() == 0 || m.getType() == 1) && !TreeUtils.exists(i.getId(), routes) && StringUtils.isEmpty(m.getParentId())).map(m -> new MenuVo(m.getId(), m.getSortNumber(), m.getPath(), m.getPath(), "", m.getIsHidden(), StringUtils.hasText(m.getComponent()) ? m.getComponent() : "Layout", false,
                                new MenuVo.MenuMeta(m.getName(), m.getIcon()), new TreeSet<>(Comparator.comparing(MenuVo::getId)))).collect(Collectors.toCollection(TreeSet::new));
                        if (!CollectionUtils.isEmpty(parent)) {
                            Set<Menu> allMenu = menus.stream().filter(m -> (m.getType() == 0 || m.getType() == 1) && StringUtils.hasText(m.getParentId())).collect(Collectors.toSet());
                            TreeUtils.findChildren(parent, allMenu);
                            routes.addAll(parent);
                        }
                    }
                });
                userVo.setPerms(perms);
                userVo.setRoutes(routes);
            }
            return userVo;
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int resetPassword(String id, String password) {
        String encode = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password);
        return repository.updatePasswordById(id, encode);
    }

}
