/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/3/28  17:18
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.gateway.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gw_route")
public class RouteDefinitionInfo {

    @Id
    @NotEmpty
    private String id;

    @OneToMany(mappedBy="route",fetch= FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PredicateAndFilterInfo> predicateAndFilterInfoList = new ArrayList<>();

    @NotNull
    private URI uri;

    @Column(name = "sort_number")
    private int sortNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PredicateAndFilterInfo> getPredicateAndFilterInfoList() {
        return predicateAndFilterInfoList;
    }

    public void setPredicateAndFilterInfoList(List<PredicateAndFilterInfo> predicateAndFilterInfoList) {
        this.predicateAndFilterInfoList = predicateAndFilterInfoList;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public int getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(int sortNumber) {
        this.sortNumber = sortNumber;
    }
}
