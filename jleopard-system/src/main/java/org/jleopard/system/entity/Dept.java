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
@Table(name = "sys_dept")
@ApiModel(value="Dept", description="sys_dept",parent = JLEntity.class)
public class Dept extends JLEntity implements Serializable {

	/**
	 * 32位随机ID
	 */
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	@ApiModelProperty(value="32位随机ID")
	private String id;

	/**
	 * 部门名称
	 */
	@ApiModelProperty(value="部门名称")
	@DynamicQuery(type = DynamicQuery.Type.LIKE)
	private String name;

	/**
	 * 部门编码
	 */
	@ApiModelProperty(value="部门编码")
	private String code;

	/**
	 * 全称
	 */
	@Column(name = "long_code")
	@ApiModelProperty(value="长编码")
	private String longCode;

	/**
	 * 简称
	 */
	@Column(name = "short_name")
	@ApiModelProperty(value="简称")
	private String shortName;

	/**
	 * 全称
	 */
	@Column(name = "display_name")
	@ApiModelProperty(value="全称")
	private String displayName;

	/**
	 * 税码
	 */
	@Column(name = "tax_code")
	@ApiModelProperty(value="税码")
	private String taxCode;

	/**
	 * 上级ID
	 */
	@Column(name = "parent_id")
	@ApiModelProperty(value="上级ID")
	private String parentId;

	/**
	 * 层级
	 */
	@ApiModelProperty(value="层级")
	private Integer rank;

	/**
	 * 是否叶子节点
	 */
	@ApiModelProperty(value="是否叶子节点")
	private Boolean leaf;

	/**
	 * 是否启用
	 */
	@ApiModelProperty(value="是否启用")
	private Boolean enabled;

	/**
	 * 排序
	 */
	@Column(name = "sort_number")
	@ApiModelProperty(value="排序")
	private Integer sortNumber;

	/**
	 * 标识
	 */
	@ApiModelProperty(value="标识")
	private Integer category;

	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remark;

	/**
	 * 是否法人公司
	 */
	@Column(name = "people_company")
	@ApiModelProperty(value="是否法人公司")
	private Boolean peopleCompany;

	@JsonIgnore
	@ManyToMany(mappedBy = "depts")
	private Set<Role> roles;

	@Transient
	@DynamicQuery(blurry = "code,name")
	private String codeOrName;

}
