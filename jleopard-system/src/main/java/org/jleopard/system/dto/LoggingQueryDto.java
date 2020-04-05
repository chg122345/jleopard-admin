/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/3  17:49
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.dto;

import lombok.Data;
import org.jleopard.data.annotation.DynamicQuery;

import java.util.Date;
import java.util.List;

@Data
public class LoggingQueryDto {

    @DynamicQuery(type = DynamicQuery.Type.LIKE)
    private String userAccount;

    @DynamicQuery
    private Integer type;

    @DynamicQuery(name = "created", type = DynamicQuery.Type.BETWEEN)
    private List<Date> created;
}
