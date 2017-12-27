package com.toplyh.server.model.repository;

import com.toplyh.server.model.entity.project.member.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by 我 on 2017/11/29.
 */
public interface MemberRepository extends CrudRepository<Member,Integer> {
    List<Member> findByProjectId(Integer id);
    List<Member> findByUserId(Integer id);
    Member findByProjectIdAndUserId(Integer projectId,Integer userId);
}
