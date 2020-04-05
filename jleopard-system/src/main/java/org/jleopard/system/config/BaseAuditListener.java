/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/4/5  15:44
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.config;

import org.jleopard.data.strategy.AbstractAuditListener;
import org.jleopard.system.util.SecurityContextUtils;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class BaseAuditListener extends AbstractAuditListener {

    @PrePersist
    @Override
    public void prePersist(Object object) throws IllegalAccessException {
        fillMeta(object, "creator", SecurityContextUtils.getCurrentUserId().orElseGet(String::new), true);
    }

    @PreUpdate
    @Override
    public void preUpdate(Object object) throws IllegalAccessException {
        fillMeta(object, "modifier", SecurityContextUtils.getCurrentUserId().orElseGet(String::new), true);
    }
}
