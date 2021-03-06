package com.toplyh.server.model.repository;

import com.toplyh.server.model.entity.project.sprint.Sprint;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by 我 on 2017/12/3.
 */
public interface SprintRepository extends CrudRepository<Sprint,Integer> {
    List<Sprint> findByProjectId(Integer id);
    List<Sprint> findByProjectIdAndMemberId(Integer projectId,Integer memberId);
    List<Sprint> findByProjectIdAndStatus(Integer projectId, Sprint.SprintStatus status);
    List<Sprint> findByProjectIdAndMemberIdAndStatus(Integer projectId, Integer memberId, Sprint.SprintStatus status);
    boolean existsByStatus(Sprint.SprintStatus status);
}
