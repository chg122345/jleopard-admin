/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/3  15:51
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.monitor.service.impl;

import org.jleopard.data.base.JLServiceImpl;
import org.jleopard.data.query.QueryWrapper;
import org.jleopard.monitor.entity.Logging;
import org.jleopard.monitor.query.LoggingQueryDto;
import org.jleopard.monitor.repository.LoggingRepository;
import org.jleopard.monitor.service.LoggingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.CompletableFuture;

@Service
public class LoggingServiceImpl extends JLServiceImpl<Logging, String, LoggingRepository> implements LoggingService {

    @Async
    @Override
    public CompletableFuture<Logging> asyncSaveLogging(Logging entity) {
        Logging save = repository.save(entity);
        return CompletableFuture.completedFuture(save);
    }

    @Override
    public void deleteAllByType(Integer type) {
        if (ObjectUtils.isEmpty(type)) {
            repository.deleteAll();
        }
        repository.deleteAllByType(type);
    }

    @Override
    public Page<Logging> queryAll(LoggingQueryDto logging, int page, int size) {
        return repository.findAll((root, q, cb) -> QueryWrapper.getPredicate(root, logging, cb), PageRequest.of(page, size, Sort.by(Sort.Order.desc("created"))));
    }
}
