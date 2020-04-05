/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/3  15:31
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.util;

import lombok.experimental.UtilityClass;
import org.jleopard.web.exception.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@UtilityClass
public class SecurityContextUtils {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Map<String, ?> getPrincipal() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("获取用户失败");
        }
        Map<String, Object> map = (Map<String, Object>) authentication.getPrincipal();
        if (CollectionUtils.isEmpty(map)) {
            throw new RuntimeException("获取用户失败");
        }
        return map;
    }

    public Optional<String> getCurrentUserId() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }
        Map<String, Object> map = (Map<String, Object>) authentication.getPrincipal();
        if (CollectionUtils.isEmpty(map)) {
           return Optional.empty();
        }
        return Optional.of((String)map.get("id"));
    }
}
