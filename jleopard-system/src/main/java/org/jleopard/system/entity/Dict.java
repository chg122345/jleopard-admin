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
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "sys_dict")
@ApiModel(value="Dict", description="sys_dict",parent = JLEntity.class)
public class Dict extends JLEntity implements Serializable {

	/**
	 * id
	 */
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	@ApiModelProperty(value="id")
	private String id;

	/**
	 * 字典编码
	 */
	@ApiModelProperty(value="字典编码")
	private String code;

	/**
	 * 字典名称
	 */
	@DynamicQuery(type = DynamicQuery.Type.LIKE)
	@Column(name = "name",nullable = false,unique = true)
	@ApiModelProperty(value="字典名称")
	private String name;

	/**
	 * 排序
	 */
	@ApiModelProperty(value="排序")
	@Column(name = "sort_number")
	private Integer sortNumber;

	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remark;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	@OneToMany(mappedBy = "dict",cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	private List<DictDetail> dictDetails;

}
