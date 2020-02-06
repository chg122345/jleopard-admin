/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2019/4/23  17:11
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.auth.security;

import lombok.extern.slf4j.Slf4j;
import org.jleopard.auth.service.FeignUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


@Component("jwtUserService")
@Slf4j
public class JwtUserServiceImpl implements UserDetailsService {

    @Autowired
    private FeignUserService feignUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = feignUserService.userByName(username);
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException(username + " Not Found");
        }
        return user;
    }

}
