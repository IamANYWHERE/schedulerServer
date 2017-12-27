package com.toplyh.server.service.normal;

import com.toplyh.server.model.entity.project.story.Story;

import com.toplyh.server.model.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 我 on 2017/11/29.
 */
@Service
public class StoryService {

    private StoryRepository storyRepository;

    @Autowired
    public StoryService(StoryRepository storyRepository){
        this.storyRepository=storyRepository;
    }


    public Story add(Story story){
        storyRepository.save(story);
        return storyRepository.findOne(story.getId());
    }

    public Story findById(Integer id){
        return storyRepository.findOne(id);
    }

    public List<Story> findByProjectId(Integer id){
        return storyRepository.findByProjectId(id);
    }
}
