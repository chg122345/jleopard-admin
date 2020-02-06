/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/1  13:58
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.service.ipml;

import org.jleopard.data.base.JLServiceImpl;
import org.jleopard.system.entity.Dict;
import org.jleopard.system.entity.DictDetail;
import org.jleopard.system.repository.DictDetailRepository;
import org.jleopard.system.service.DictDetailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DictDetailServiceImpl extends JLServiceImpl<DictDetail, String, DictDetailRepository> implements DictDetailService {

    @Override
    public Page<DictDetail> findAllByDict(Dict dict, int page, int size) {
        return repository.findAllByDict(dict, PageRequest.of(page, size, Sort.by(Sort.Order.asc("sortNumber"))));
    }

}
