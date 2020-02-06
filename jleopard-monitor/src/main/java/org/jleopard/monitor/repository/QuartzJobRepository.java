/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/3  20:56
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.monitor.repository;

import org.jleopard.data.base.JLRepository;
import org.jleopard.monitor.entity.QuartzJob;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuartzJobRepository extends JLRepository<QuartzJob, String> {

    List<QuartzJob> findByPauseIsFalse();
}
