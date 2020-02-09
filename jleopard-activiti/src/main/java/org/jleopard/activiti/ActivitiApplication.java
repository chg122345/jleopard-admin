/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/8  20:21
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.activiti;

import org.jleopard.activiti.config.JsonpCallbackFilter;
import org.jleopard.cloud.EnableJLeopardCloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {
        org.activiti.spring.boot.SecurityAutoConfiguration.class
})
@EnableJLeopardCloud
public class ActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }

    @Bean
    public JsonpCallbackFilter filter(){
        return new JsonpCallbackFilter();
    }
}
