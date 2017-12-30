package com.toplyh.server.api;

import com.alibaba.fastjson.JSONObject;
import com.toplyh.server.api.state.APIState;
import com.toplyh.server.model.entity.User;
import com.toplyh.server.model.entity.project.Project;
import com.toplyh.server.model.entity.project.member.Member;
import com.toplyh.server.model.entity.project.sprint.Sprint;
import com.toplyh.server.model.json.data.AddSprint;
import com.toplyh.server.model.json.data.SimpProject;
import com.toplyh.server.model.json.data.SimpSprint;
import com.toplyh.server.service.normal.AuthenticationService;
import com.toplyh.server.service.normal.MemberService;
import com.toplyh.server.service.normal.ProjectService;
import com.toplyh.server.service.normal.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by 我 on 2017/12/3.
 */
@RestController
@RequestMapping("/api/sprint")
public class SprintApi {

    private AuthenticationService authenticationService;
    private SprintService sprintService;
    private ProjectService projectService;
    private MemberService memberService;

    @Autowired
    public SprintApi(AuthenticationService authenticationService,
                     SprintService sprintService,
                     ProjectService projectService,
                     MemberService memberService){
        this.authenticationService=authenticationService;
        this.sprintService=sprintService;
        this.projectService=projectService;
        this.memberService=memberService;
    }

    @PostMapping("/add")
    public Object addSprint(@RequestHeader(value = "token") String token,
                            @RequestBody AddSprint aSprint){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        Project project=projectService.findById(aSprint.getProjectId());
        Member member=memberService.findById(aSprint.getMemberId());
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else if (project==null){
            jsonObject.put("state",APIState.SPRINT_NO_PROJECT);
        }else if (member==null){
            jsonObject.put("state",APIState.SPRINT_NO_MEMBER);
        }else {
            Sprint sprint=new Sprint();
            sprint.setProject(project);
            sprint.setMember(member);
            sprint.setWorkTime(aSprint.getSprint().getWorkTime());
            sprint.setStatus(Sprint.SprintStatus.valueOf(aSprint.getSprint().getStatus()));
            sprint.setDdl(aSprint.getSprint().getDdl());
            sprint.setContent(aSprint.getSprint().getContent());
            sprint.setName(aSprint.getSprint().getName());
            sprintService.add(sprint);
            jsonObject.put("state",APIState.SPRINT_ADD_RIGHT);
        }
        return jsonObject;
    }

    @GetMapping("/show-by-project")
    public Object showSprintByProjectId(@RequestHeader(value = "token") String token,
                             @RequestParam(value = "projectId") Integer id){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        List<Sprint> sprints=sprintService.findByProjectId(id);
        jsonObject.put("sprints",sprints);
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else if (!projectService.exists(id)){
            jsonObject.put("state",APIState.SPRINT_NO_PROJECT);
        }else if (sprints==null){
            jsonObject.put("state",APIState.SPRINT_NO_SPRINT);
        }else {
            jsonObject.put("state",APIState.STORY_SHOW_RIGHT);
        }
        return jsonObject;
    }

    @GetMapping("/show-by-project-and-member")
    public Object showSprintByProjectIdAndMemberId(@RequestHeader(value = "token") String token,
                                                   @RequestParam(value = "projectId") Integer projectId,
                                                   @RequestParam(value = "memberId") Integer memberId){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        List<Sprint> sprints=sprintService.findByProjectIdAndMemberId(projectId,memberId);
        jsonObject.put("sprints",sprints);
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else if (!projectService.exists(projectId)){
            jsonObject.put("state",APIState.SPRINT_NO_PROJECT);
        }else if (!memberService.exists(memberId)){
            jsonObject.put("state",APIState.SPRINT_NO_MEMBER);
        }else if (sprints==null){
            jsonObject.put("state",APIState.SPRINT_NO_SPRINT);
        }else {
            jsonObject.put("state",APIState.SPRINT_SHOW_RIGHT);
        }
        return jsonObject;
    }

    @GetMapping("/show-by-project-and-status")
    public Object getSprint(@RequestHeader(value = "token") String token,
                              @RequestParam(value = "projectId") Integer projectId,
                              @RequestParam(value = "status") String status){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        List<Sprint> sprints=sprintService.findByProjectIdAndStatus(projectId, Sprint.SprintStatus.valueOf(status));
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else if (!projectService.exists(projectId)){
            jsonObject.put("state",APIState.SPRINT_NO_PROJECT);
        }else if (!sprintService.existsByStatus(Sprint.SprintStatus.valueOf(status))){
            jsonObject.put("state",APIState.SPRINT_NO_STATUS);
        }else if (sprints==null){
            jsonObject.put("state",APIState.SPRINT_NO_SPRINT);
        }else {
            jsonObject.put("state",APIState.SPRINT_SHOW_RIGHT);
            List<SimpSprint> simpSprint=new ArrayList<>();
            Project project=projectService.findById(projectId);
            if (user.getId().equals(project.getId())){
                for (Sprint s :
                        sprints) {
                    SimpSprint sim=new SimpSprint();
                    sim.setName(s.getName());
                    sim.setContent(s.getContent());
                    sim.setDdl(s.getDdl());
                    sim.setId(s.getId());
                    sim.setStatus(s.getStatus());
                    sim.setUsername(project.getUser().getName());
                    sim.setWorkTime(s.getWorkTime());
                    simpSprint.add(sim);
                }
            }else {
                Set<Member> members=project.getMembers();
                Iterator<Member> mit=members.iterator();
                Member m=null;
                while (mit.hasNext()){
                    Member member=mit.next();
                    if (user.getId().equals(member.getUser().getId())){
                        m=member;
                    }
                }

                if (m!=null){
                    List<Sprint> sps=sprintService.findByProjectIdAndMemberId(projectId,m.getId());
                    for (Sprint s :
                            sps) {
                        SimpSprint sim=new SimpSprint();
                        sim.setName(s.getName());
                        sim.setContent(s.getContent());
                        sim.setDdl(s.getDdl());
                        sim.setId(s.getId());
                        sim.setStatus(s.getStatus());
                        sim.setUsername(project.getUser().getName());
                        sim.setWorkTime(s.getWorkTime());
                        simpSprint.add(sim);
                    }
                }
            }
            jsonObject.put("data",simpSprint);
        }
        return jsonObject;
    }

    @GetMapping("/new")
    public Object newSprint(@RequestHeader(value = "token") String token){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        if (user==null){
            jsonObject.put("state", APIState.AUTHENTICATION_TOKEN_ERROR);
        }else {
            Sprint sprint=new Sprint();
            sprint.setName("吃东西");
            sprint.setContent("ssssss");
            sprint.setDdl(new Date());
            sprint.setStatus(Sprint.SprintStatus.BO);
            sprint.setWorkTime(3);

            jsonObject.put("sprint",sprint);
        }
        return jsonObject;
    }
}
