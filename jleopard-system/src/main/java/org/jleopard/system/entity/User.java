package org.jleopard.system.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.jleopard.common.base.JLEntity;
import org.jleopard.data.annotation.DynamicQuery;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "sys_user")
@ApiModel(value="User", description="sys_user",parent = JLEntity.class)
public class User extends JLEntity implements Serializable {

	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	@ApiModelProperty
	private String id;

	/**
	 * 姓名
	 */
	@ApiModelProperty(value="姓名")
	@DynamicQuery(type = DynamicQuery.Type.LIKE)
	private String name;

	/**
	 * 手机号
	 */
	@ApiModelProperty(value="手机号")
	private String phone;

	/**
	 * 编码
	 */
	@ApiModelProperty(value="编码")
	private String code;

	/**
	 * 登录密码
	 */
	@ApiModelProperty(value="登录密码")
	private String password;

	/**
	 * 员工账号
	 */
	@ApiModelProperty(value="账号")
	private String account;

	/**
	 * 账户类型
	 */
	@ApiModelProperty(value="账户类型")
	private String type;

	/**
	 * 证件类型
	 */
	@ApiModelProperty(value="证件类型")
	private String certificateType;

	/**
	 * 证件编号
	 */
	@ApiModelProperty(value="证件编号")
	private String certificateNumber;

	/**
	 * 头像
	 */
	@ApiModelProperty(value="头像")
	private String avatar;

	/**
	 * 性别
	 */
	@ApiModelProperty(value="性别")
	private Integer sex;

	/**
	 * 职位ID
	 */
//	@ApiModelProperty(value="职位ID")
//	@Column(name = "job_id")
//	private String jobId;

	/**
	 * 部门ID
	 */
//	@ApiModelProperty(value="部门ID")
//	@Column(name = "dept_id")
//	private String deptId;

	/**
	 * 是否在职
	 */
	@ApiModelProperty(value="是否在职")
	@Column(name = "on_job")
	private Boolean onJob;

	/**
	 * 生日
	 */
	@ApiModelProperty(value="生日", example = "2020-01-01")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private java.util.Date birthday;

	/**
	 * 邮箱
	 */
	@ApiModelProperty(value="邮箱")
	private String email;

	/**
	 * 职位
	 */
	@ApiModelProperty(value="职位")
	private String duty;

	/**
	 * 岗位
	 */
	@ApiModelProperty(value="岗位")
	private String postName;

	/**
	 * 排序号
	 */
	@ApiModelProperty(value="排序号")
	private Integer sortNumber;

	/**
	 * 是否启用
	 */
	@ApiModelProperty(value="是否启用")
	private Boolean enabled;

	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remark;


	@OneToOne
	@JoinColumn(name = "job_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
	private Job job;

	@OneToOne
	@JoinColumn(name = "dept_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
	private Dept dept;

	@ManyToMany(fetch= FetchType.LAZY)
	@JoinTable(name = "sys_user_role", joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
	@ApiModelProperty(hidden = true)
	private Set<Role> roles;

	public @interface Update {}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User user = (User) o;
		return Objects.equals(id, user.id) &&
				Objects.equals(account, user.account);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, account);
	}

}
