package com.toplyh.server.model.repository;

import com.toplyh.server.model.entity.project.meeting.Meeting;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by 我 on 2017/11/30.
 */
public interface MeetingRepository extends CrudRepository<Meeting,Integer> {
    List<Meeting> findByProjectId(Integer id);
}
