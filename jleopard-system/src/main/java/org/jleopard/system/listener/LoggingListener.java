/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/3  14:59
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.listener;

import lombok.extern.slf4j.Slf4j;
import org.jleopard.logging.entity.Logging;
import org.jleopard.logging.event.LoggingEvent;
import org.jleopard.system.feign.FeignLoggingService;
import org.jleopard.system.util.SecurityContextUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class LoggingListener {

    @Value("${spring.application.name:'jleopard-system'}")
    private String applicationName;

    private final FeignLoggingService loggingService;

    public LoggingListener(FeignLoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @EventListener(LoggingEvent.class)
    public void pushLogging(LoggingEvent event) {
        Logging source = (Logging) event.getSource();
        source.setInstanceServer(applicationName);
        try {
            Map<String, ?> principal = SecurityContextUtils.getPrincipal();
            source.setUserAccount((String) principal.get("account"));
            loggingService.saveLog(source);
        }catch (Exception ignored) {
        }
    }
}
