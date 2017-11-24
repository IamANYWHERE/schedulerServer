package com.toplyh.server.model.repository;

import com.toplyh.server.model.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 我 on 2017/11/21.
 */

public interface UserRepository extends CrudRepository<User,Integer> {

    User findByName(String name);

    @Modifying
    @Transactional
    @Query("Update User u set u.id=?1 where u.token=?2")
    int updateTokenById(Integer id,String token);
}
