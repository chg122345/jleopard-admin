/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/1/28  20:09
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.service.ipml;

import org.jleopard.data.base.JLServiceImpl;
import org.jleopard.data.query.QueryWrapper;
import org.jleopard.system.entity.Dept;
import org.jleopard.system.repository.DeptRepository;
import org.jleopard.system.service.DeptService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@CacheConfig(cacheNames = "dept")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptServiceImpl extends JLServiceImpl<Dept,String, DeptRepository> implements DeptService {

    @Override
    public Page<Dept> findByCodeOrName(String codeOrName, int page, int size) {
        if (StringUtils.isEmpty(codeOrName)) {
            return repository.findAll(PageRequest.of(page, size, Sort.Direction.DESC,"created"));
        }
        Dept dept = new Dept();
        dept.setCodeOrName(codeOrName);
        return repository.findAll(QueryWrapper.of(dept), PageRequest.of(page, size, Sort.Direction.DESC,"created"));
    }

    @Override
    public List<Dept> findByCodeOrNameToTree(String codeOrName) {
        Dept dept = new Dept();
        dept.setCodeOrName(codeOrName);
        List<Dept> list = repository.findAll(QueryWrapper.of(dept));

        return null;
    }
}
