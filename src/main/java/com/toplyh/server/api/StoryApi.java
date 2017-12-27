package com.toplyh.server.api;

import com.alibaba.fastjson.JSONObject;
import com.toplyh.server.api.state.APIState;
import com.toplyh.server.model.entity.User;
import com.toplyh.server.model.entity.project.Project;
import com.toplyh.server.model.entity.project.story.Story;
import com.toplyh.server.model.json.data.AddStory;
import com.toplyh.server.service.normal.AuthenticationService;
import com.toplyh.server.service.normal.ProjectService;
import com.toplyh.server.service.normal.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 我 on 2017/11/29.
 */
@RestController
@RequestMapping("/api/story")
public class StoryApi {

    private AuthenticationService authenticationService;
    private ProjectService projectService;
    private StoryService storyService;

    @Autowired
    public StoryApi(AuthenticationService authenticationService,
                    ProjectService projectService,
                    StoryService storyService){
        this.authenticationService=authenticationService;
        this.projectService=projectService;
        this.storyService=storyService;
    }

    @PostMapping("/add")
    public Object addStory(@RequestHeader(value = "token") String token,
                           @RequestBody AddStory aStory){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        Project projectInDB=projectService.findById(aStory.getProjectId());
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else if (projectInDB==null){
            jsonObject.put("state",APIState.STORY_NO_PROJECT);
        } else {
            Story story=new Story();
            story.setStatus(Story.StoryStatus.valueOf(aStory.getStory().getStatus()));
            story.setDescription(aStory.getStory().getDescription());
            story.setProject(projectInDB);
            storyService.add(story);
            jsonObject.put("state",APIState.STORY_ADD_RIGHT);
        }
        return jsonObject;
    }

    @GetMapping("/show")
    public Object showStory(@RequestHeader(value = "token") String token,
                            @RequestParam(value = "projectId") Integer id){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        List<Story> stories=storyService.findByProjectId(id);
        jsonObject.put("stories",stories);
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else if (!projectService.exists(id)){
            jsonObject.put("state",APIState.STORY_NO_PROJECT);
        }else if (stories==null){
            jsonObject.put("state",APIState.STORY_NO_STORY);
        } else {
            jsonObject.put("state",APIState.STORY_SHOW_RIGHT);
        }
        return jsonObject;
    }

    @GetMapping("/new")
    public Object newStory(@RequestHeader(value = "token") String token){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        if (user==null){
            jsonObject.put("state", APIState.AUTHENTICATION_TOKEN_ERROR);
        }else {
            Story story=new Story();
            story.setDescription("加油");
            story.setStatus(Story.StoryStatus.YES);
            jsonObject.put("story",story);
        }
        return jsonObject;
    }
}
