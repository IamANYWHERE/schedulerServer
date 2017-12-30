package com.toplyh.server.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.toplyh.server.api.state.APIState;
import com.toplyh.server.model.entity.project.Project;
import com.toplyh.server.model.entity.User;
import com.toplyh.server.model.entity.project.member.Member;
import com.toplyh.server.model.json.data.ProjectAndMember;
import com.toplyh.server.model.json.data.SimpProject;
import com.toplyh.server.service.normal.AuthenticationService;
import com.toplyh.server.service.normal.ProjectService;
import com.toplyh.server.service.normal.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by 我 on 2017/11/28.
 */

@RestController
@RequestMapping("/api/project")
public class ProjectApi {

    private AuthenticationService authenticationService;
    private UserService userService;
    private ProjectService projectService;

    public ProjectApi(AuthenticationService authenticationService,
                      UserService userService,
                      ProjectService projectService){
        this.authenticationService=authenticationService;
        this.projectService=projectService;
        this.userService=userService;
    }

    @PostMapping("/add")
    public Object addProject(@RequestHeader(value = "token") String token,
                             @RequestBody SimpProject simpProject){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        if (user==null){
            jsonObject.put("state", APIState.AUTHENTICATION_TOKEN_ERROR);
        }else{
            Project project=new Project();
            project.setDdl(simpProject.getDdl());
            project.setProgress(simpProject.getProgress());
            project.setProjectName(simpProject.getProjectName());
            project.setUser(user);
            Project projectInDB=projectService.add(project);
            jsonObject.put("state",APIState.PROJECT_ADD_RIGHT);
            jsonObject.put("projectInDB",projectInDB);
        }
        return jsonObject;
    }

    @PostMapping("/addProjectAndMember")
    public Object addProjectAndMember(@RequestHeader(value = "token") String token,
                                      @RequestBody ProjectAndMember projectAndMember){
        System.out.println("addProjectAndMember");
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else {
            Project project=new Project();
            project.setProgress(0);
            project.setProjectName(projectAndMember.getProjectName());
            project.setDdl(new Date(projectAndMember.getDdl()));
            project.setUser(user);

            List<String> memNames=projectAndMember.getMembers();
            Set<Member> members=new HashSet<>();
            for (int i=0;i<memNames.size();i++){
                User muser=userService.findByName(memNames.get(i));
                if (muser==null){
                    continue;
                }
                Member member=new Member();
                member.setUser(muser);
                member.setContribution(0);
                members.add(member);
            }
            project.setMembers(members);
            projectService.add(project);
            jsonObject.put("state",APIState.PROJECT_ADD_RIGHT);
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

    @GetMapping("/all")
    public Object getAllProjects(@RequestHeader(value = "token") String token){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        JSONObject jsonData=new JSONObject();
        JSONArray jsonArraySelf=new JSONArray();
        JSONArray jsonArrayOther=new JSONArray();
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
            jsonObject.put("data",null);
        }else {
            Set<Project> projects = user.getProjects();
            Iterator<Project> pit = projects.iterator();

            Set<Member> members = user.getMembers();
            Iterator<Member> mit = members.iterator();
            List<Project> mprojects = new ArrayList<>();
            while (mit.hasNext()) {
                mprojects.add(mit.next().getProject());
            }

            for (int i = 0; i < mprojects.size(); i++) {
                SimpProject sp = new SimpProject(mprojects.get(i));
                jsonArrayOther.add(sp);
            }

            while (pit.hasNext()) {
                JSONObject jsonProject = new JSONObject();
                Project project = pit.next();
                SimpProject simpProject = new SimpProject(project);
                jsonArraySelf.add(simpProject);
            }
            jsonData.fluentPut("selfProjects",jsonArraySelf)
                    .fluentPut("otherProjects",jsonArrayOther);

            jsonObject.fluentPut("state",APIState.PROJECT_SHOW_RIGHT)
                    .fluentPut("data",jsonData);

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
            SimpProject simpProject=new SimpProject();
            simpProject.setProjectName("prokkkkk");
            simpProject.setDdl(new Date());
            simpProject.setProgress(2);

            jsonObject.put("name","aaaa");
            jsonObject.put("password","12355");
        }
        return jsonObject;
    }
}
