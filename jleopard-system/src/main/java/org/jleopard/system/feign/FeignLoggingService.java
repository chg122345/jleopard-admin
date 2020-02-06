/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/3  21:09
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.feign;

import org.jleopard.logging.entity.Logging;
import org.jleopard.system.feign.fallback.FeignLoggingServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "JLEOPARD-MONITOR",fallback = FeignLoggingServiceFallback.class)
public interface FeignLoggingService {

    @PostMapping(value = "/log")
    Logging saveLog(@RequestBody Logging logging);
}
