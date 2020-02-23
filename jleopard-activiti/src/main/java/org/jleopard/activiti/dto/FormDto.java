/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/9  17:50
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.activiti.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class FormDto implements Serializable {

    //任务名称
    private String actName;

    //派遣人
    private String assignee;


    //流程实例ID
    private String procInstId;


    //任务ID
    private String taskId;

    //表单属性
    private List<Map<String, String>> process;
}
