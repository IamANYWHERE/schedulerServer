package com.toplyh.server.api;

import com.alibaba.fastjson.JSONObject;
import com.toplyh.server.api.state.APIState;
import com.toplyh.server.model.entity.project.Project;
import com.toplyh.server.model.entity.User;
import com.toplyh.server.service.normal.AuthenticationService;
import com.toplyh.server.service.normal.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by 我 on 2017/11/28.
 */

@RestController
@RequestMapping("/api/project")
public class ProjectApi {

    private AuthenticationService authenticationService;

    private ProjectService projectService;

    public ProjectApi(AuthenticationService authenticationService,
                      ProjectService projectService){
        this.authenticationService=authenticationService;
        this.projectService=projectService;
    }

    @PostMapping("/add")
    public Object addProject(@RequestHeader(value = "token") String token,
                             @RequestBody Project project){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        if (user==null){
            jsonObject.put("state", APIState.AUTHENTICATION_TOKEN_ERROR);
        }else{
            project.setUser(user);
            Project projectInDB=projectService.add(project);
            jsonObject.put("state",APIState.PROJECT_ADD_RIGHT);
            jsonObject.put("projectInDB",projectInDB);
        }
        return jsonObject;
    }

    @GetMapping("/show")
    public Object showProjects(@RequestHeader(value = "token") String token){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        List<Project> projects=null;
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
            jsonObject.put("projects",projects);
        }else {
            projects=projectService.findByUserName(user.getName());
            jsonObject.put("state",APIState.PROJECT_SHOW_RIGHT);
            jsonObject.put("projects",projects);
        }
        return jsonObject;
    }

    @GetMapping("/new")
    public Object newProject(@RequestHeader(value = "token") String token){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else {
            Project project=new Project();
            project.setUser(user);
            project.setDdl(new Date());
            project.setProjectName("和九");
            jsonObject.put("project",project);
        }
        return jsonObject;
    }
}
