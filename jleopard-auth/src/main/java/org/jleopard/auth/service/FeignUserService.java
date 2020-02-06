/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2019/4/29  16:33
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.auth.service;

import org.jleopard.auth.security.User;
import org.jleopard.auth.service.impl.FeignUserServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "JLEOPARD-SYSTEM",fallback = FeignUserServiceImpl.class)
public interface FeignUserService {

    @GetMapping(value = "/user/auth")
    User userByName(@RequestParam("account") String account);

    @GetMapping(value = "/oauth/permissions/user")
    List<String> getAuthorityByUser(@RequestParam("userId") Long userId);
}
