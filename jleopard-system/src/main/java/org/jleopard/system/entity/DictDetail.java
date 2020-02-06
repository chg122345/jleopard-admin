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
@Table(name = "sys_dict_detail")
@ApiModel(value="DictDetail", description="sys_dict_detail",parent = JLEntity.class)
public class DictDetail extends JLEntity implements Serializable {

	/**
	 * id
	 */
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	@ApiModelProperty(value="id")
	private String id;


	/** 字典标签 */
	@DynamicQuery(type = DynamicQuery.Type.LIKE)
	@ApiModelProperty(value="字典标签编码")
	@Column(name = "label",nullable = false)
	private String label;

	/** 字典值 */
	@ApiModelProperty(value="字典值")
	@Column(name = "value",nullable = false)
	private String value;

	/**
	 * 字典分类ID
	 */
	@ApiModelProperty(value="字典ID")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "dict_id")
	@DynamicQuery
	private Dict dict;

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

}
