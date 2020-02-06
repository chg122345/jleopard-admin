/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/1/21  15:42
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.service;

import org.jleopard.common.base.JLService;
import org.jleopard.system.entity.User;
import org.jleopard.system.vo.UserAuthVo;
import org.jleopard.system.vo.UserVo;

public interface UserService extends JLService<User, String> {

    User findByUsername(String name);

    UserAuthVo findByAccount(String account);

    UserVo findUserInfo(String id);

    int resetPassword(String id, String password);
}
