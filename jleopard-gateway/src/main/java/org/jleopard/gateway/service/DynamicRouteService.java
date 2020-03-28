/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/3/28  17:03
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.gateway.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jleopard.gateway.entity.PredicateAndFilterInfo;
import org.jleopard.gateway.entity.RouteDefinitionInfo;
import org.jleopard.gateway.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DynamicRouteService implements RouteDefinitionLocator, ApplicationEventPublisherAware {

    @Autowired
    private RouteRepository routeRepository;

    private ApplicationEventPublisher publisher;

    private List<RouteDefinition> routeDefinitionList;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.fromIterable(routeDefinitionList);
    }

   /* public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap((r) -> {
            List<PredicateAndFilterInfo> list = new ArrayList<>();
            List<FilterDefinition> filters = r.getFilters();
            List<PredicateDefinition> predicates = r.getPredicates();
            if (!CollectionUtils.isEmpty(predicates)) {
                list.addAll(predicates.stream().map(f -> {
                    PredicateAndFilterInfo predicateAndFilterInfo = new PredicateAndFilterInfo();
                    predicateAndFilterInfo.setType(1);
                    predicateAndFilterInfo.setName(f.getName());
                   if (!CollectionUtils.isEmpty(f.getArgs())) {
                       try {
                           predicateAndFilterInfo.setJsonArgs(mapper.writeValueAsString(f.getArgs()));
                       } catch (JsonProcessingException e) {
                           e.printStackTrace();
                       }
                   }
                    return predicateAndFilterInfo;
                }).collect(Collectors.toList()));
            }
            if (!CollectionUtils.isEmpty(filters)) {
                list.addAll(filters.stream().map(f -> {
                    PredicateAndFilterInfo predicateAndFilterInfo = new PredicateAndFilterInfo();
                    predicateAndFilterInfo.setType(2);
                    predicateAndFilterInfo.setName(f.getName());
                   if (!CollectionUtils.isEmpty(f.getArgs())) {
                       try {
                           predicateAndFilterInfo.setJsonArgs(mapper.writeValueAsString(f.getArgs()));
                       } catch (JsonProcessingException e) {
                           e.printStackTrace();
                       }
                   }
                    return predicateAndFilterInfo;
                }).collect(Collectors.toList()));
            }
            RouteDefinitionInfo routeInfo = new RouteDefinitionInfo();
            routeInfo.setId(r.getId());
            routeInfo.setUri(r.getUri());
            routeInfo.setSortNumber(r.getOrder());
            routeInfo.setPredicateAndFilterInfoList(list);
            routeRepository.save(routeInfo);
            notifyRefreshRoutes();
            return Mono.empty();
        });
    }

    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap((id) -> {
            if (this.routeRepository.findById(id).isPresent()) {
                this.routeRepository.deleteById(id);
                notifyRefreshRoutes();
                return Mono.empty();
            } else {
                return Mono.defer(() -> Mono.error(new NotFoundException("RouteDefinition not found: " + routeId)));
            }
        });
    }*/

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }


    @PostConstruct
    public void init() {
        load();
    }

    /**
     * 刷新配置
     */
    public void notifyRefreshRoutes() {
        load();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    /**
     * 加载
     */
    private void load() {
        List<RouteDefinitionInfo> all = routeRepository.findAll();
        List<RouteDefinition> collect = all.stream().map(i -> {
            List<PredicateAndFilterInfo> predicateAndFilterInfoList = i.getPredicateAndFilterInfoList();
            List<PredicateAndFilterInfo> Predicates = predicateAndFilterInfoList.stream().filter(pf -> pf.getType() == 1).collect(Collectors.toList());
            List<PredicateAndFilterInfo> filters = predicateAndFilterInfoList.stream().filter(pf -> pf.getType() == 2).collect(Collectors.toList());
            List<PredicateDefinition> predicateDefinitionList = Predicates.stream().map(pr -> {
                PredicateDefinition predicateDefinition = new PredicateDefinition();
                predicateDefinition.setName(pr.getName());
                if (StringUtils.hasText(pr.getJsonArgs())) {
                    try {
                        predicateDefinition.setArgs(mapper.readValue(pr.getJsonArgs(), LinkedHashMap.class));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return predicateDefinition;
            }).collect(Collectors.toList());
            List<FilterDefinition> filterDefinitionList = filters.stream().map(ft -> {
                FilterDefinition filterDefinition = new FilterDefinition();
                filterDefinition.setName(ft.getName());
                if (StringUtils.hasText(ft.getJsonArgs())) {
                    try {
                        filterDefinition.setArgs(mapper.readValue(ft.getJsonArgs(), LinkedHashMap.class));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return filterDefinition;
            }).collect(Collectors.toList());
            RouteDefinition route = new RouteDefinition();
            route.setId(i.getId());
            route.setOrder(i.getSortNumber());
            route.setUri(i.getUri());
            route.setPredicates(predicateDefinitionList);
            route.setFilters(filterDefinitionList);
            return route;
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect)) {
            routeDefinitionList = collect;
        } else {
            routeDefinitionList = new ArrayList<>();
        }
    }
}
