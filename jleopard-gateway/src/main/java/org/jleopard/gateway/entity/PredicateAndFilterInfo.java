/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/3/28  17:29
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.gateway.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "gw_predicate_filter")
public class PredicateAndFilterInfo {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    /**
     * 路由id
     */
    @ManyToOne(fetch=FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name="route_id")
    private RouteDefinitionInfo route;

    /**
     * 类型 1 断言  2 过滤器
     */
    @Column(length = 1, columnDefinition = "int")
    private Integer type;

    @NotNull
    private String name;

    @Column(name = "args", columnDefinition = "text")
    private String jsonArgs;

    @Column(name = "sort_number")
    private int sortNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RouteDefinitionInfo getRoute() {
        return route;
    }

    public void setRoute(RouteDefinitionInfo route) {
        this.route = route;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJsonArgs() {
        return jsonArgs;
    }

    public void setJsonArgs(String jsonArgs) {
        this.jsonArgs = jsonArgs;
    }

    public int getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(int sortNumber) {
        this.sortNumber = sortNumber;
    }
}
