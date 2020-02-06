/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/3  20:19
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jleopard.common.base.JLEntity;
import org.jleopard.quartz.JLQuartzJobDetail;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@DynamicUpdate
@Entity
@Table(name = "sys_quartz_job")
public class QuartzJob extends JLEntity implements JLQuartzJobDetail, Serializable {

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    @NotNull(groups = {Update.class})
    private Long id;

    /**
     * 定时器名称
     */
    @Column(name = "job_name")
    private String jobName;

    /**
     * Bean名称
     */
    @Column(name = "bean_name")
    @NotBlank
    private String beanName;

    /**
     * 方法名称
     */
    @Column(name = "method_name")
    @NotBlank
    private String methodName;

    /**
     * 参数
     */
    @Column(name = "params")
    private String params;

    /**
     * cron表达式
     */
    @Column(name = "cron_expression")
    @NotBlank
    private String cronExpression;

    /**
     * 状态
     */
    @Column(name = "is_pause")
    private Boolean pause = false;

    @Override
    public Boolean isPause() {
        return this.pause;
    }

    /**
     * 备注
     */
    @Column(name = "remark")
    @NotBlank
    private String remark;

    public interface Update{}
}
