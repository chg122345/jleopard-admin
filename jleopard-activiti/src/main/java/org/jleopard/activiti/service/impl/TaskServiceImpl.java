/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/23  11:50
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.activiti.service.impl;

import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.jleopard.activiti.service.TaskService;
import org.jleopard.activiti.utils.Pagination;
import org.jleopard.activiti.utils.ProcessUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final org.activiti.engine.TaskService atTaskService;

    private final String[] fields = new String[]{"id", "owner", "assignee", "parentTaskId", "name", "localizedName", "localizedDescription",
            "description", "createTime", "dueDate", "suspensionState", "category", "isIdentityLinksInitialized", "taskIdentityLinkEntities",
            "processInstanceId", "processDefinitionId", "taskDefinitionKey", "formKey", "isCanceled", "claimTime","taskLocalVariables","processVariables"};

    @Autowired(required = false)
    public TaskServiceImpl(org.activiti.engine.TaskService atTaskService) {
        this.atTaskService = atTaskService;
    }

    @Override
    public List<Task> findAll() {
        atTaskService.createTaskQuery()
                .processDefinitionKey("holiday")
                .list();
        return null;
    }

    @Override
    public List<Map<String, Object>> findAllByCandidateOrAssigned(String candidateOrAssigned) {
        List<Task> list = atTaskService.createTaskQuery()
                .taskCandidateOrAssigned(candidateOrAssigned)
                .orderByTaskCreateTime().asc()
                .list();
        return list.stream().map(i -> ProcessUtils.obj2Map(i, fields)).collect(Collectors.toList());
    }

    @Override
    public Pagination findCurrentUserAllTask(String name, Pagination pagination) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("获取用户失败");
        }
        Map<String, Object> map = (Map<String, Object>) authentication.getPrincipal();
        if (CollectionUtils.isEmpty(map)) {
            throw new RuntimeException("获取用户失败");
        }
        TaskQuery taskQuery = atTaskService.createTaskQuery().taskAssignee((String) map.get("username"));
        if (StringUtils.hasText(name)) {
            taskQuery.taskNameLike("%" + name + "%");
        }
        int count = (int) taskQuery.count();
        pagination.setTotalElements(count);
        if (count == 0) {
            return pagination;
        }
        int totalPages = (int) Math.ceil(count / pagination.getSize());
        pagination.setTotalPages(totalPages);
        List<Task> list = taskQuery.orderByTaskCreateTime().asc()
                .listPage(pagination.getStart(), pagination.getEnd());
        List<Map<String, Object>> collect = list.stream().map(i -> ProcessUtils.obj2Map(i, fields)).collect(Collectors.toList());
        pagination.setContent(collect);
        return pagination;
    }

    @Override
    public void complete(String id, Map<String, Object> variables) {
        atTaskService.complete(id, variables, true);
    }
}
