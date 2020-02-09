/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/2  14:36
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuVo implements Comparable<MenuVo> {

    @JsonIgnore
    private String id;
    @JsonIgnore
    private Integer sort = 999;
    private String name;
    private String path;
    private String redirect;
    private Boolean hidden;
    private String component;
    private Boolean alwaysShow;
    private MenuMeta meta;
    private Set<MenuVo> children;

    @Override
    public int compareTo(MenuVo o) {
        Integer len1 = this.sort;
        Integer len2 = o.getSort();
        return (len1 - len2 > 0) ? 1 : -1 ;
    }

    @Data
    public static class MenuMeta {
        private String title;
        private String icon;
        private boolean breadcrumb;
        private String activePath;
        public MenuMeta(String title, String icon) {
            this.title = title;
            this.icon = icon;
        }
        public MenuMeta(String title, String icon, boolean breadcrumb, String activePath) {
            this.title = title;
            this.icon = icon;
            this.breadcrumb = breadcrumb;
            this.activePath = activePath;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuVo menuVo = (MenuVo) o;
        return Objects.equals(id, menuVo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
