/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/4  16:08
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.monitor.quartz;

import org.jleopard.monitor.entity.QuartzJob;
import org.jleopard.monitor.repository.QuartzJobRepository;
import org.jleopard.quartz.QuartzManage;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuartzRunner implements ApplicationRunner {

    private final QuartzJobRepository quartzJobRepository;

    private final QuartzManage quartzManage;

    public QuartzRunner(QuartzJobRepository quartzJobRepository, QuartzManage quartzManage) {
        this.quartzJobRepository = quartzJobRepository;
        quartzManage.setJobClass(ExecutionQuartzJob.class);
        this.quartzManage = quartzManage;
    }

    /**
     * 项目启动时重新激活启用的定时任务
     */
    @Override
    public void run(ApplicationArguments args){
        System.out.println("--------------------开始注入定时任务---------------------");
        List<QuartzJob> quartzJobs = quartzJobRepository.findByPauseIsFalse();
        quartzJobs.forEach(quartzManage::addJob);
        System.out.println("--------------------定时任务注入完成---------------------");
    }
}
