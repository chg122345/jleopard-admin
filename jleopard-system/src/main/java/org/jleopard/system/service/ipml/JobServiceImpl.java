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
import org.jleopard.system.entity.Job;
import org.jleopard.system.repository.JobRepository;
import org.jleopard.system.service.JobService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "job")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobServiceImpl extends JLServiceImpl<Job,String, JobRepository> implements JobService {

    @Override
    public Page<Job> findByCodeOrName(String codeOrName, String deptId, Integer page, Integer size) {
        if (StringUtils.isEmpty(codeOrName) && StringUtils.isEmpty(deptId)) {
            return repository.findAll(PageRequest.of(page, size, Sort.Direction.DESC,"created"));
        }
        Job job = new Job();
        job.setCodeOrName(codeOrName);
        if (!StringUtils.isEmpty(deptId)) {
            Dept dept = new Dept();
            dept.setId(deptId);
            job.setDept(dept);
        }
        return this.repository.findAll(QueryWrapper.of(job), PageRequest.of(page, size, Sort.Direction.DESC,"created"));
    }
}
