/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/23  11:19
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.activiti.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class ProcessDto implements Serializable {

    private String id;

    private String key;

    private String businessKey;

//    private String variableName;

    private Map<String,Object> variables;
}
