/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/3  15:51
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.monitor.service;

import org.jleopard.common.base.JLService;
import org.jleopard.monitor.entity.Logging;
import org.jleopard.monitor.query.LoggingQueryDto;
import org.springframework.data.domain.Page;

import java.util.concurrent.CompletableFuture;

public interface LoggingService extends JLService<Logging, String> {

    CompletableFuture<Logging> asyncSaveLogging(Logging entity);

    void deleteAllByType(Integer type);

    Page<Logging> queryAll(LoggingQueryDto logging, int page, int size);
}
