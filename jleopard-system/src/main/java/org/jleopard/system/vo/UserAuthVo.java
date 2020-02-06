/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/1/27  17:36
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.vo;

import lombok.Data;

import java.util.Set;

@Data
public class UserAuthVo {

    private  String id;

    private String name;

    private  String account;

    private String password;

    private String email;

    private String phone;

    private Set<String> perms;

    private Boolean enabled;

}
