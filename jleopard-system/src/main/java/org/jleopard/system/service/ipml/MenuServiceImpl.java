/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/1/31  15:57
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.service.ipml;

import org.jleopard.data.base.JLServiceImpl;
import org.jleopard.system.entity.Menu;
import org.jleopard.system.repository.MenuRepository;
import org.jleopard.system.service.MenuService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@CacheConfig(cacheNames = "menu")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl extends JLServiceImpl<Menu, String, MenuRepository> implements MenuService {

    @Override
    public List<Menu> findAllByType(List<Integer> type) {
        if (CollectionUtils.isEmpty(type)) {
            return repository.findAll(Sort.by(Sort.Order.asc("sortNumber")));
        }
        return repository.findAllByTypeIn(type,Sort.by(Sort.Order.asc("sortNumber")));
    }
}
