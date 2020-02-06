/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/3  21:11
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.feign.fallback;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.jleopard.system.feign.FeignLoggingService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FeignLoggingServiceFallback implements FallbackFactory<FeignLoggingService> {
    @Override
    public FeignLoggingService create(Throwable throwable) {
        log.error("连接日志服务保存日志失败",throwable);
        return logging -> null;
    }
}
