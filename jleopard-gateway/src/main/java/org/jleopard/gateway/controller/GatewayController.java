/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/3/28  20:44
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.gateway.controller;

import org.jleopard.gateway.entity.RouteDefinitionInfo;
import org.jleopard.gateway.repository.RouteRepository;
import org.jleopard.gateway.service.DynamicRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private DynamicRouteService routeService;


    @PostMapping
    public Mono<RouteDefinitionInfo> save(@RequestBody RouteDefinitionInfo route) {
        RouteDefinitionInfo routeDefinitionInfo = routeRepository.save(route);
        routeService.notifyRefreshRoutes();
        return Mono.just(routeDefinitionInfo);
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable String id) {
        routeRepository.deleteById(id);
        routeService.notifyRefreshRoutes();
        return Mono.empty();
    }

    @GetMapping
    public Flux<RouteDefinitionInfo> list() {
        List<RouteDefinitionInfo> all = routeRepository.findAll();
        return Flux.fromIterable(all);
    }

}
