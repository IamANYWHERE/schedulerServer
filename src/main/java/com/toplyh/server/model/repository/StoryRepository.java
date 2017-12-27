package com.toplyh.server.model.repository;

import com.toplyh.server.model.entity.project.story.Story;
import org.springframework.data.repository.CrudRepository;

import java.awt.*;
import java.util.List;

/**
 * Created by 我 on 2017/11/29.
 */
public interface StoryRepository extends CrudRepository<Story,Integer>{
    List<Story> findByProjectId(Integer id);
}
