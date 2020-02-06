/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/1  13:58
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.service;

import org.jleopard.common.base.JLService;
import org.jleopard.system.entity.Dict;
import org.jleopard.system.entity.DictDetail;
import org.springframework.data.domain.Page;

public interface DictDetailService extends JLService<DictDetail, String> {

    Page<DictDetail> findAllByDict(Dict dict, int page, int size);
}
