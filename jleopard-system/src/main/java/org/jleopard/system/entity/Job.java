/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/1/21  15:10
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.jleopard.common.base.JLEntity;
import org.jleopard.data.annotation.DynamicQuery;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "sys_job")
@ApiModel(value="Job", description="sys_job",parent = JLEntity.class)
public class Job extends JLEntity implements Serializable {

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    @ApiModelProperty
    private String id;

    @DynamicQuery(type = DynamicQuery.Type.LIKE)
    private String name;

    private String code;

    @Column(name = "sort_number")
    private Integer sortNumber;

    private Boolean enabled;

    @DynamicQuery
    @OneToOne
    @JoinColumn(name = "dept_id", foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private Dept dept;

    @Transient
    @DynamicQuery(blurry = "name,code")
    private String codeOrName;

}
