/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/3  20:59
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.monitor.service;

import org.jleopard.common.base.JLService;
import org.jleopard.monitor.entity.QuartzJob;

import java.util.Set;

public interface QuartzJobService extends JLService<QuartzJob, String> {

    QuartzJob create(QuartzJob resources);

    QuartzJob updateJob(QuartzJob resources);

    QuartzJob updateIsPause(String id);

    void deleteJobByIds(Set<String> ids);

    void execution(String id);
}
