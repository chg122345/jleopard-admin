/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/1/27  17:11
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.auth;

import org.jleopard.cloud.EnableJLeopardCloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJLeopardCloud
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class,args);
    }
}
