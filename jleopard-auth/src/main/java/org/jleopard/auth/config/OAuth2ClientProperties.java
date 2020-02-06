/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2019/4/23  16:46
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(
        prefix = "security.oauth2.client"
)
@Data
public class OAuth2ClientProperties {

    private String provider;
    private String clientId;
    private String clientSecret;
    private String clientAuthenticationMethod;
    private Set<String> authorities;
    private Set<String> authorizationGrantType;
    private String redirectUriTemplate;
    private Set<String> scope;
    private String clientName;

}

