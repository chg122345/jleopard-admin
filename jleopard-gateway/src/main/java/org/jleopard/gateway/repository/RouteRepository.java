/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/3/28  18:15
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.gateway.repository;

import org.jleopard.gateway.entity.RouteDefinitionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<RouteDefinitionInfo, String> {
}
