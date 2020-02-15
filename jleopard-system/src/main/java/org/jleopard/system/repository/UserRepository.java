/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/1/21  15:40
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.system.repository;

import org.jleopard.data.base.JLRepository;
import org.jleopard.system.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JLRepository<User, String> {

    User findByNameIs(String username);

    User findOneByAccount(String account);

    @Query(value = "select u.* from sys_user u LEFT JOIN sys_user_role ur ON u.id = ur.user_id WHERE ur.role_id = :roleId AND if(:name IS NOT NULL, u.name LIKE %:name%, 1 = 1)", nativeQuery = true)
    Page<User> findByRoleId(@Param("roleId") String roleId, @Param("name") String name, Pageable page);

    @Modifying
    @Query("update User set password=:password where id=:id")
    int updatePasswordById(@Param("id") String id, @Param("password") String password);

    @Modifying
    @Query(value = "delete from sys_user_role where role_id=:roleId AND user_id = :userId", nativeQuery = true)
    int removeRole(@Param("roleId") String id, @Param("userId") String userId);
}
