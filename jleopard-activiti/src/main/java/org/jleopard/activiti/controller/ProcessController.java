/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/9  17:33
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.activiti.controller;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.jleopard.activiti.dto.FormDto;
import org.jleopard.activiti.utils.ProcessUtils;
import org.jleopard.resource.annotation.JLAnonymousAccess;
import org.jleopard.web.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("process")
public class ProcessController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private FormService formService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;

    /**
     * 流程列表
     * @param model
     * @return
     */
    @GetMapping
    public ResponseEntity<?> startPage(org.springframework.ui.Model model) {
        repositoryService.createProcessDefinitionQuery().list();
        return ResponseEntity.ok("");
    }


    @RequestMapping("startProcess/{id}")
    public String startProcess(@PathVariable("id") String id, org.springframework.ui.Model model) {
        //按照流程定义ID加载流程开启时候需要的表单信息
        StartFormData startFormData = formService.getStartFormData(id);
        List<FormProperty> formProperties = startFormData.getFormProperties();

        //流程定义ID
        model.addAttribute("processesId", id);
        model.addAttribute("form", formProperties);

        return "startProcess";
    }


    /**
     * @Description: 启动一个新的流程
     * @Param: [id]
     */
    @RequestMapping("startProcesses")
    public ResponseEntity<?> startProcesses(@RequestParam Map<String, Object> param) {

        String processesId = (String) param.get("processesId");
        //流程提交人 这里模拟
        String userId = (String) param.get("userId");
        param.remove("processesId");

//        Execution last = runtimeService.createExecutionQuery().processInstanceBusinessKey(userId).processDefinitionId(processesId).singleResult();
//        if (null != last) {
//            return JsonUtil.getFailJson("请勿重复提交！");
//        }

        ProcessInstance pi = runtimeService.startProcessInstanceById(processesId, userId, param);

        if (null == pi) {
           throw new BadRequestException("流程启动失败！");
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * 组装表单过程的表单信息
     **/
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

    /**
     * 生成流程图
     **/
    @GetMapping("img/{id}")
    @JLAnonymousAccess
    public ResponseEntity<String> generateProcessImg(@PathVariable(value = "id") String processInstanceId) throws IOException {

        //获取历史流程实例
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());

        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());

        List<HistoricActivityInstance> highLightedActivitList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
        //高亮环节id集合
        List<String> highLightedActivitis = new ArrayList<>();

        //高亮线路id集合
        List<String> highLightedFlows = ProcessUtils.getHighLightedFlows(bpmnModel, definitionEntity, highLightedActivitList);

        for (HistoricActivityInstance tempActivity : highLightedActivitList) {
            String activityId = tempActivity.getActivityId();
            highLightedActivitis.add(activityId);
        }
        //配置字体
        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis, highLightedFlows, "宋体", "微软雅黑", "黑体", null, 2.0);
        BufferedImage bi = ImageIO.read(imageStream);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bi, "png", bos);
        //转换成字节
        byte[] bytes = bos.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        //转换成base64串
        String png_base64 = encoder.encodeBuffer(bytes);
        //删除 \r\n
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");

        bos.close();
        imageStream.close();
        return ResponseEntity.ok(png_base64);
    }


    /*public List<String> getHighLightedFlows(
            ProcessDefinitionEntity processDefinitionEntity,
            List<HistoricActivityInstance> historicActivityInstances) {

        // 用以保存高亮的线flowId
        List<String> highFlows = new ArrayList<String>();
        // 对历史流程节点进行遍历
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {
            // 得到节点定义的详细信息
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i)
                            .getActivityId());
            // 用以保存后需开始时间相同的节点
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();
            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i + 1)
                            .getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                // 后续第一个节点
                HistoricActivityInstance activityImpl1 = historicActivityInstances
                        .get(j);
                // 后续第二个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances
                        .get(j + 1);
                // 如果第一个节点和第二个节点开始时间相同保存
                if (activityImpl1.getStartTime().equals(
                        activityImpl2.getStartTime())) {
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            // 取出节点的所有出去的线
            List<PvmTransition> pvmTransitions = activityImpl
                    .getOutgoingTransitions();
            // 对所有的线进行遍历
            for (PvmTransition pvmTransition : pvmTransitions) {
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
                        .getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }*/
}
