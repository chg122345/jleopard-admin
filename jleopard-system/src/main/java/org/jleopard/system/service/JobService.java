/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/1/28  20:09
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.service;

import org.jleopard.common.base.JLService;
import org.jleopard.system.entity.Job;
import org.springframework.data.domain.Page;

public interface JobService extends JLService<Job, String> {

    Page<Job> findByCodeOrName(String codeOrName, String deptId, Integer page, Integer size);
}
