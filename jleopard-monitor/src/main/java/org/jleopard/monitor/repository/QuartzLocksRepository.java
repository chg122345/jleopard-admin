/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/3  20:57
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.monitor.repository;

import org.jleopard.monitor.entity.QuartzLocks;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;

@Repository
public interface QuartzLocksRepository extends CrudRepository<QuartzLocks, String> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO mt_quartz_locks(job_id,job_name,state) VALUES(:#{#quartzLocks.jobId},:#{#quartzLocks.jobName},:#{#quartzLocks.state})", nativeQuery = true)
    int insertLocks(QuartzLocks quartzLocks);

    @Transactional
    @Modifying
    @Query("update QuartzLocks w set w.state=:state where w.jobId=:id")
    void updateState(@Param("id") String id, @Param("state") Integer state);
}
