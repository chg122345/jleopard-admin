package org.jleopard.system.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "sys_menu")
@ApiModel(value="Menu", description="sys_menu",parent = JLEntity.class)
public class Menu extends JLEntity implements Serializable {

	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	@ApiModelProperty
	private String id;

	@ApiModelProperty("名称")
	@DynamicQuery(type = DynamicQuery.Type.LIKE)
	private String name;

	@ApiModelProperty("权限编码")
	private String perms;

	@ApiModelProperty("前端路径")
	private String path;

	@ApiModelProperty("前端组件路径")
	private String component;

	@ApiModelProperty("上级id")
	private String parentId;

	@ApiModelProperty("图标")
	private String icon;

	@Column(name = "sort_number")
	@ApiModelProperty(value = "排序号")
	private Integer sortNumber;

	/**
	 * 0 目录，1 菜单，2 权限
	 */
	@ApiModelProperty("权限类型 0 目录，1 菜单，2 权限")
	@Column(length = 1)
	private Integer type;

	/** 是否为外链 true/false */
	@Column(columnDefinition = "bit(1) default 0", name = "i_frame")
	@ApiModelProperty(value = "是否为外链")
	private Boolean iFrame;
	/**
	 * 是否启用
	 */
	@Column(columnDefinition = "bit(1) default 0")
	@ApiModelProperty(value="是否启用")
	private Boolean enabled;

	@Column(columnDefinition = "bit(1) default 0")
	private Boolean isHidden;

	@ManyToMany(mappedBy = "menus")
	@JsonIgnore
	private Set<Role> roles;

}
