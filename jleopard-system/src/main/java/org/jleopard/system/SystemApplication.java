/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/1/21  11:16
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system;

import org.jleopard.cloud.EnableJLeopardCloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"org.jleopard.system.entity"})
@EnableJLeopardCloud
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class,args);
    }
}
