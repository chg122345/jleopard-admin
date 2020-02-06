/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/1/31  16:00
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.service.ipml;

import org.jleopard.data.base.JLServiceImpl;
import org.jleopard.system.entity.Role;
import org.jleopard.system.repository.RoleRepository;
import org.jleopard.system.service.RoleService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames = "role")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl extends JLServiceImpl<Role, String, RoleRepository> implements RoleService {
}
