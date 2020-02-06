/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/1/27  21:15
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jleopard.system.entity.Dept;
import org.jleopard.system.entity.Job;

import java.util.Set;


@Data
public class UserVo {

    private  String id;

    private  String account;

    private String email;

    private Set<String> perms;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 编码
     */
    private String code;

    /**
     * 账户类型
     */
    private String type;

    /**
     * 证件类型
     */
    private String certificateType;

    /**
     * 证件编号
     */
    private String certificateNumber;

    /**
     * 头像
     */
    private String avatar;

    private Integer sex;

    /**
     * 是否在职
     */
    private Boolean onJob;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date birthday;

    private String duty;

    private String postName;

    private Integer sortNumber;

    private Boolean enabled;

    private String remark;

    private Job job;

    private Dept dept;

    private Set<MenuVo> routes;
}
