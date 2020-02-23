/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/23  10:50
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.activiti.service;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.jleopard.activiti.dto.FormDto;
import org.jleopard.activiti.dto.ProcessDto;
import org.jleopard.activiti.utils.Pagination;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ProcessService {

    ProcessInstance start(ProcessDto processDto);

    List<ProcessDefinition> findAll();

    Pagination<Map<String, Object>> findAll(int page, int size, String name);

    void deleteByDeploymentId(String deploymentId);

    /**
     * 激活/挂起 流程
     */
    String activateOrSuspendById(String id, Boolean suspend);

    /**
     * 生成流程图
     */
    void generateProcessImgById(String id, HttpServletResponse response) throws IOException;

    void generateProcessImgByInstanceId(String instanceId, HttpServletResponse response) throws IOException;

    List<HistoricActivityInstance> getHistoricActivityInstance(String processInstanceId);

    List<FormDto> assembleProcessForm(String processInstanceId);
}
