/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/23  11:49
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.activiti.service;

import org.activiti.engine.task.Task;
import org.jleopard.activiti.utils.Pagination;

import java.util.List;
import java.util.Map;

public interface TaskService {

    List<Task> findAll();

    List<Map<String,Object>> findAllByCandidateOrAssigned(String candidateOrAssigned);

    Pagination<Map<String,Object>> findCurrentUserAllTask(String name, Pagination pagination);

    void complete(String id, Map<String,Object> variables);


}
