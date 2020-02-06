/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/4  12:48
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.monitor.quartz;

import org.jleopard.monitor.service.QuartzJobService;
import org.jleopard.quartz.JLQuartzJobDetail;
import org.jleopard.quartz.QuartzManage;
import org.jleopard.quartz.utils.SpringApplicationContext;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Async
@DisallowConcurrentExecution
public class ExecutionQuartzJob extends QuartzJobBean {

    private static final Logger log = LoggerFactory.getLogger(org.jleopard.quartz.config.ExecutionQuartzJob.class);

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final QuartzJobService quartzJobService = SpringApplicationContext.getBean(QuartzJobService.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JLQuartzJobDetail quartzJob = (JLQuartzJobDetail) jobExecutionContext.getMergedJobDataMap().get(JLQuartzJobDetail.JOB_KEY);
        try {
            Future<?> future = executorService.submit(() -> {
                quartzJobService.execution((String)quartzJob.getId());
                return null;
            });
            future.get();
        } catch (Exception e) {
//            QuartzManage quartzManage = SpringApplicationContext.getBean(QuartzManage.class);
//            quartzManage.pauseJob(quartzJob);
            log.error("任务执行失败，任务名称：{}", quartzJob.getJobName(),e);
        }
    }
}
