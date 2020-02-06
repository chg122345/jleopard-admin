/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2019/4/29  16:34
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.auth.service.impl;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.jleopard.auth.security.User;
import org.jleopard.auth.service.FeignUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FeignUserServiceImpl implements FallbackFactory<FeignUserService> {

    @Override
    public FeignUserService create(Throwable throwable) {
        log.error("获取用户服务失败",throwable);
        final String errorMsg = throwable.getMessage();
       return new FeignUserService() {
           @Override
           public User userByName(String name) {
               User user = new User();
               user.setName(errorMsg);
               return user;
           }

           @Override
           public List<String> getAuthorityByUser(Long userId) {
               return null;
           }
       };
    }
}

