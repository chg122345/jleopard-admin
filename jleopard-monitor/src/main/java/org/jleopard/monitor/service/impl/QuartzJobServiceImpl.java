/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/3  21:01
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.monitor.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jleopard.data.base.JLServiceImpl;
import org.jleopard.monitor.entity.QuartzJob;
import org.jleopard.monitor.entity.QuartzLocks;
import org.jleopard.monitor.quartz.ExecutionQuartzJob;
import org.jleopard.monitor.repository.QuartzJobRepository;
import org.jleopard.monitor.repository.QuartzLocksRepository;
import org.jleopard.monitor.service.QuartzJobService;
import org.jleopard.quartz.QuartzManage;
import org.jleopard.web.exception.BadRequestException;
import org.quartz.CronExpression;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class QuartzJobServiceImpl extends JLServiceImpl<QuartzJob, String, QuartzJobRepository> implements QuartzJobService {

    private final QuartzManage quartzManage;

    private final RestTemplate restTemplate;

    private final QuartzLocksRepository locksRepository;

    public QuartzJobServiceImpl(QuartzManage quartzManage, RestTemplate restTemplate, QuartzLocksRepository locksRepository) {
        quartzManage.setJobClass(ExecutionQuartzJob.class);
        this.quartzManage = quartzManage;
        this.restTemplate = restTemplate;
        this.locksRepository = locksRepository;
    }

    @Override
    public void execution(String id) {
        Optional<QuartzJob> job = findById(id);
        if (!job.isPresent()) {
            log.info("定时任务不存在");
            throw new BadRequestException("定时任务不存在");
        } else {
            QuartzJob quartzJob = job.get();
            String instanceServer = quartzJob.getInstanceServer();
            if (StringUtils.isEmpty(instanceServer)) {
                log.error("定时任务：" + quartzJob.getJobName() + " 服务实例为空");
                throw new BadRequestException("定时任务：" + quartzJob.getJobName() + " 服务实例为空");
            }
            try {
                QuartzLocks locks = new QuartzLocks();
                locks.setJobId(quartzJob.getId());
                locks.setJobName(quartzJob.getJobName());
                locks.setState(1);
                locksRepository.insertLocks(locks);
                long startTime = System.currentTimeMillis();
                log.info("任务准备执行，任务名称：{}", quartzJob.getJobName());
                restTemplate.postForEntity("http://" + instanceServer + "/execJob", quartzJob, Map.class);
                long times = System.currentTimeMillis() - startTime;
                log.info("任务执行完毕，任务名称：{} 总共耗时：{} 毫秒", quartzJob.getJobName(), times);
            } catch (DataIntegrityViolationException exception) {
                log.error("定时任务：" + quartzJob.getJobName() + " 已经在执行中", exception);
                throw new BadRequestException(HttpStatus.SERVICE_UNAVAILABLE, "定时任务：" + quartzJob.getJobName() + "已经在执行，请勿重复执行");
            } catch (IllegalStateException ex){
                log.error("定时任务：" + quartzJob.getJobName() + " 执行失败", ex);
                locksRepository.deleteById(quartzJob.getId());
                throw new BadRequestException(HttpStatus.SERVICE_UNAVAILABLE, "定时任务：" + quartzJob.getJobName() + " 服务实例未找到");
            } catch (RestClientException e) {
                log.error("定时任务：" + quartzJob.getJobName() + " 执行失败", e);
                locksRepository.deleteById(quartzJob.getId());
                throw new BadRequestException(HttpStatus.SERVICE_UNAVAILABLE, "定时任务：" + quartzJob.getJobName() + " 执行失败" + e.getMessage());
            }
            locksRepository.deleteById(quartzJob.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuartzJob create(QuartzJob resources) {
        if (!CronExpression.isValidExpression(resources.getCronExpression())){
            throw new BadRequestException("cron表达式格式错误");
        }
        resources = repository.save(resources);
        quartzManage.addJob(resources);
        return resources;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuartzJob updateJob(QuartzJob resources) {
        if (!CronExpression.isValidExpression(resources.getCronExpression())){
            throw new BadRequestException("cron表达式格式错误");
        }
        resources = dynamicUpdate(resources.getId(), resources);
        quartzManage.updateJobCron(resources);
        return resources;
    }

    @Override
    public QuartzJob updateIsPause(String id) {
        Optional<QuartzJob> byId = findById(id);
        if (byId.isPresent()) {
            QuartzJob quartzJob = byId.get();
            if (quartzJob.isPause()) {
                quartzManage.resumeJob(quartzJob);
                quartzJob.setPause(false);
            } else {
                quartzManage.pauseJob(quartzJob);
                quartzJob.setPause(true);
            }
            return repository.save(quartzJob);
        } else {
            throw new BadRequestException("ID: " + id + "不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJobByIds(Set<String> ids) {
        for (String id : ids) {
            findById(id).ifPresent(quartzManage::deleteJob);
        }
        deleteBatchByIds(ids);
    }
}
