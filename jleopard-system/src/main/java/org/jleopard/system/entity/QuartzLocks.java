/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/3  20:21
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "sys_quartz_locks")
public class QuartzLocks implements Serializable {

    @Id
    @Column(name = "job_id")
    private String jobId;

    @Column(name = "job_name")
    private String jobName;

    private Integer state;
}
