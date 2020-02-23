/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/9  14:04
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.activiti.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Pagination<T> {

    public static int DEFAULT_PAGE = 0;
    public static int DEFAULT_SIZE = 10;

    private List<T> content = Lists.newArrayList(); //当前返回的记录列表
    private int totalElements = 0; //总记录数
    private int totalPages = 0; //总页数

    protected int page = DEFAULT_PAGE;  //页码
    protected int size = DEFAULT_SIZE; //每页记录数

    public Pagination() {
        this(DEFAULT_PAGE, DEFAULT_SIZE);
    }

    public Pagination(int page, int size) {
        setPage(page);
        setSize(size);
    }

    public static Pagination of(int page, int size) {
        return new Pagination<>(page, size);
    }

    public static Pagination getInstance2Top(int top) {
        return new Pagination<>(DEFAULT_PAGE, top);
    }

    @JsonIgnore
    public int getStart(){
        return getPage()*getSize();
    }

    @JsonIgnore
    public int getEnd(){
        return getStart() + getSize();
    }
}

