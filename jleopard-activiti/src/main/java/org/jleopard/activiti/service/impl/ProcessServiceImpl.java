/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/23  11:05
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.activiti.service.impl;

import lombok.Getter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.jleopard.activiti.dto.FormDto;
import org.jleopard.activiti.dto.ProcessDto;
import org.jleopard.activiti.service.ProcessService;
import org.jleopard.activiti.utils.Pagination;
import org.jleopard.activiti.utils.ProcessUtils;
import org.jleopard.web.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Getter
public class ProcessServiceImpl implements ProcessService {

    private final RepositoryService repositoryService;

    private final RuntimeService runtimeService;

    private final HistoryService historyService;

    private final ProcessEngineConfiguration processEngineConfiguration;

    public ProcessServiceImpl(RepositoryService repositoryService, RuntimeService runtimeService, HistoryService historyService, ProcessEngineConfiguration processEngineConfiguration) {
        this.repositoryService = repositoryService;
        this.runtimeService = runtimeService;
        this.historyService = historyService;
        this.processEngineConfiguration = processEngineConfiguration;
    }

    @Override
    public ProcessInstance start(ProcessDto processDto) {
        String id = processDto.getId();
        String key = processDto.getKey();
        String businessKey = processDto.getBusinessKey();
        ExecutionQuery executionQuery = runtimeService.createExecutionQuery().processInstanceBusinessKey(businessKey);
        ProcessInstance processInstance = null;
        if(StringUtils.hasText(id)) {
            Execution last = executionQuery.processDefinitionId(id).singleResult();
            if (null != last) {
                throw new BadRequestException("请勿重复提交！");
            }
            processInstance = runtimeService.startProcessInstanceById(id, businessKey, processDto.getVariables());
        } else if (StringUtils.hasText(key)) {
            Execution last = executionQuery.processDefinitionKey(key).singleResult();
            if (null != last) {
                throw new BadRequestException("请勿重复提交！");
            }
            processInstance = runtimeService.startProcessInstanceByKey(key, businessKey, processDto.getVariables());
        }
        if (null == processInstance) {
            throw new BadRequestException("流程启动失败！");
        }
        return processInstance;
    }


    @Override
    public List<ProcessDefinition> findAll() {
        return repositoryService.createProcessDefinitionQuery().list();
    }

    @Override
    public Pagination<Map<String, Object>> findAll(int page, int size, String name) {
        Pagination<Map<String,Object>> pagination = new Pagination<>(page, size);
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        if (StringUtils.hasText(name)) {
            processDefinitionQuery.processDefinitionNameLike("%" + name + "%");
        }
        int totalCount = (int) processDefinitionQuery.count();
        int totalPages = (int) Math.ceil(totalCount / size);
        pagination.setTotalElements(totalCount);
        pagination.setTotalPages(totalPages);
        List<ProcessDefinition> processDefinitions = processDefinitionQuery.listPage(pagination.getStart(), pagination.getEnd());
        List<Map<String, Object>> collect = processDefinitions.stream().map(i -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i.getId());
            map.put("category", i.getCategory());
            map.put("name", i.getName());
            map.put("key", i.getKey());
            map.put("description", i.getDescription());
            map.put("version", i.getVersion());
            map.put("resourceName", i.getResourceName());
            map.put("deploymentId", i.getDeploymentId());
            map.put("deploymentTime", i.getDeploymentId());
            map.put("diagramResourceName", i.getDiagramResourceName());
            map.put("hasStartFormKey", i.hasStartFormKey());
            map.put("hasGraphicalNotation", i.hasGraphicalNotation());
            map.put("isSuspended", i.isSuspended());
            map.put("engineVersion", i.getEngineVersion());
            return map;
        }).collect(Collectors.toList());
        pagination.setContent(collect);
        return pagination;
    }

    @Override
    public void deleteByDeploymentId(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
    }

    @Override
    public String activateOrSuspendById(String id, Boolean suspend) {
        String msg;
        if (suspend) {
            repositoryService.suspendProcessDefinitionById(id, true, new Date());
            msg = "已挂起ID为[" + id + "]的流程定义";
        } else {
            repositoryService.activateProcessDefinitionById(id, true, new Date());
            msg = "已激活ID为[" + id + "]的流程定义";
        }
        return msg;
    }

    @Override
    public void generateProcessImgById(String id, HttpServletResponse response) throws IOException {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(id);
        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        //配置字体
        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png","宋体", "微软雅黑", "黑体", null, 2.0);
        BufferedImage bi = ImageIO.read(imageStream);
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/png");
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "png", out);
        out.close();
    }

    @Override
    public void generateProcessImgByInstanceId(String instanceId, HttpServletResponse response) throws IOException {

        //获取历史流程实例
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());

        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        List<HistoricActivityInstance> highLightedActivitList = historyService.createHistoricActivityInstanceQuery().processInstanceId(instanceId).list();
        //高亮环节id集合
        List<String> highLightedActivitis = new ArrayList<>();
        //高亮线路id集合
        List<String> highLightedFlows = ProcessUtils.getHighLightedFlows(bpmnModel, highLightedActivitList);

        for (HistoricActivityInstance tempActivity : highLightedActivitList) {
            String activityId = tempActivity.getActivityId();
            highLightedActivitis.add(activityId);
        }
        //配置字体
        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis, highLightedFlows, "宋体", "微软雅黑", "黑体", null, 2.0);
        BufferedImage bi = ImageIO.read(imageStream);
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/png");
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "png", out);
        out.close();
    }

    @Override
    public List<HistoricActivityInstance> getHistoricActivityInstance(String processInstanceId) {
        return historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceEndTime().desc()
                .list();
    }


    /**
     * 组装表单过程的表单信息
     **/
    @Override
    public List<FormDto> assembleProcessForm(String processInstanceId) {

        List<HistoricActivityInstance> historys = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();

        List<FormDto> formList = new ArrayList<>();
        for (HistoricActivityInstance activity : historys) {
            String actInstanceId = activity.getId();
            FormDto form = new FormDto();
            form.setActName(activity.getActivityName());
            form.setAssignee(activity.getAssignee());
            form.setProcInstId(activity.getProcessInstanceId());
            form.setTaskId(activity.getTaskId());
            //查询表单信息
            List<Map<String, String>> maps = new LinkedList<>();
            List<HistoricDetail> processes = historyService.createHistoricDetailQuery().activityInstanceId(actInstanceId).list();
            for (HistoricDetail process : processes) {
                HistoricDetailVariableInstanceUpdateEntity pro = (HistoricDetailVariableInstanceUpdateEntity) process;
                Map<String, String> keyValue = new HashMap<>(1);
                keyValue.put(pro.getName(), pro.getTextValue());
                maps.add(keyValue);
            }
            form.setProcess(maps);
            formList.add(form);
        }

        return formList;
    }
}
