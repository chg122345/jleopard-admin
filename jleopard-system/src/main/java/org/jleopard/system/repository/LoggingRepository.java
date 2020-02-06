/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/3  15:50
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.repository;

import org.jleopard.data.base.JLRepository;
import org.jleopard.system.entity.Logging;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggingRepository extends JLRepository<Logging, String> {

    int deleteAllByType(Integer type);
}
