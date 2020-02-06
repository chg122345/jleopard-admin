package org.jleopard.monitor.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "mt_logging")
@ApiModel(value="Logging", description="sys_logging")
public class Logging implements Serializable {

	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	@ApiModelProperty
	private String id;

	@ApiModelProperty(value="服务实例")
	@Column(name = "instance_server")
	private String instanceServer;

	/**
	 * 请求IP
	 */
	@ApiModelProperty(value="请求IP")
	@Column(name = "request_ip")
	private String requestIp;

	/**
	 * 日志类型 1 操作记录 2 异常记录
	 */
	@ApiModelProperty(value="日志类型 1 操作记录 2 异常记录")
	private Integer type;

	/**
	 * 用户ID
	 */
	@ApiModelProperty(value="用户账号")
	@Column(name = "user_account")
	private String userAccount;

	/**
	 * 说明
	 */
	@ApiModelProperty(value="说明")
	private String description;

	/**
	 * 请求方法
	 */
	@Column(name = "action_method")
	@ApiModelProperty(value="请求方法")
	private String actionMethod;

	/**
	 * 请求URL
	 */
	@Column(name = "action_url")
	@ApiModelProperty(value="请求URL")
	private String actionUrl;

	/**
	 * 请求参数
	 */
	@ApiModelProperty(value="请求参数")
	@Column(columnDefinition = "text")
	private String params;

	/**
	 * 客户端信息
	 */
	@ApiModelProperty(value="客户端信息")
	private String ua;

	/**
	 * 类路径
	 */
	@Column(name = "class_path")
	@ApiModelProperty(value="类路径")
	private String classPath;

	/**
	 * 请求方法
	 */
	@Column(name = "request_method")
	@ApiModelProperty(value="请求方法")
	private String requestMethod;

	/**
	 * 请求开始时间
	 */
	@Column(name = "start_time")
	@ApiModelProperty(value="请求开始时间")
	private Date startTime;

	/**
	 * 请求完成时间
	 */
	@Column(name = "finish_time")
	@ApiModelProperty(value="请求完成时间")
	private Date finishTime;

	@Column(name = "consuming_time")
	@ApiModelProperty("消耗时长")
	private Long consumingTime;

	/**
	 * 异常消息
	 */
	@Column(name = "ex_desc")
	@ApiModelProperty(value="异常消息")
	private String exDesc;

	@Column(name = "ex_detail", columnDefinition = "text")
	@ApiModelProperty
	private byte[] exDetail;

	@Column(name = "created", updatable = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@CreationTimestamp
	@ApiModelProperty(value = "创建时间", dataType = "Date,Timestamp,String", hidden = true)
	private Date created;

}
