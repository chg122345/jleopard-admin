package org.jleopard.system.entity;


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
@Table(name = "sys_role")
@ApiModel(value="Role", description="sys_role",parent = JLEntity.class)
public class Role extends JLEntity implements Serializable {

	/**
	 * 32位随机ID
	 */
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	@ApiModelProperty(value="32位随机ID")
	private String id;

	/**
	 * 角色名
	 */
	@ApiModelProperty(value="角色名")
	@DynamicQuery(type = DynamicQuery.Type.LIKE)
	private String name;

	/**
	 * 角色编码
	 */
	@ApiModelProperty(value="角色编码")
	private String code;

	/**
	 * 1 所有 2 本级部门隔离 3 本级以及子级隔离 4自定义
	 */
	@ApiModelProperty(value="1 所有 2 本级部门隔离 3 本级以及子级隔离 4自定义")
	private Integer type;

	/**
	 * 数据隔离范围
	 */
	@ApiModelProperty(value="数据隔离范围")
	private String dsScope;

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

	@JsonIgnore
	@ManyToMany(mappedBy = "roles", fetch= FetchType.LAZY)
	private Set<User> users;

	@ManyToMany(fetch= FetchType.LAZY)
	@JoinTable(name = "sys_role_menu", joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "menu_id",referencedColumnName = "id")})
	private Set<Menu> menus;

	@ManyToMany(fetch= FetchType.LAZY)
	@JoinTable(name = "sys_role_dept", joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "dept_id",referencedColumnName = "id")})
	private Set<Dept> depts;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Role role = (Role) o;
		return Objects.equals(id, role.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
