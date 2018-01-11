package com.toplyh.server.api;

import com.alibaba.fastjson.JSONObject;
import com.toplyh.server.api.state.APIState;
import com.toplyh.server.model.entity.User;
import com.toplyh.server.model.entity.project.Project;
import com.toplyh.server.model.entity.project.member.Member;
import com.toplyh.server.model.json.data.AddMember;
import com.toplyh.server.model.json.data.ShowMember;
import com.toplyh.server.service.normal.AuthenticationService;
import com.toplyh.server.service.normal.MemberService;
import com.toplyh.server.service.normal.ProjectService;
import com.toplyh.server.service.normal.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 我 on 2017/11/29.
 */
@RestController
@RequestMapping("/api/member")
public class MemberApi {

    private AuthenticationService authenticationService;
    private MemberService memberService;
    private ProjectService projectService;
    private UserService userService;

    @Autowired
    public MemberApi(AuthenticationService authenticationService,
                     MemberService memberService,
                     ProjectService projectService,
                     UserService userService){
        this.authenticationService=authenticationService;
        this.memberService=memberService;
        this.projectService=projectService;
        this.userService=userService;
    }

    @PostMapping("/add")
    public Object addMember(@RequestHeader(value = "token") String token,
                            @RequestBody AddMember aMember){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        Project projectInDB=projectService.findById(aMember.getProjectId());
        if (user==null){
            jsonObject.put("state", APIState.AUTHENTICATION_TOKEN_ERROR);
        }else if (projectInDB==null){
            jsonObject.put("state",APIState.MEMBER_NO_PROJECT);
        }else {
            Member member=new Member();
            User userInDB=userService.findByName(aMember.getMember().getMemName());
            member.setProject(projectInDB);
            member.setContribution(aMember.getMember().getContribution());
            member.setUser(userInDB);
            memberService.add(member);
            jsonObject.put("state",APIState.MEMBER_ADD_RIGHT);
        }
        return jsonObject;
    }

    @GetMapping("/show")
    public Object showMember(@RequestHeader(value = "token") String token,
                             @RequestParam(value = "projectId") Integer id){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        List<Member> members=memberService.findByProjectId(id);
        List<ShowMember> showMembers=new ArrayList<>();
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else if (!projectService.exists(id)){
            jsonObject.put("state",APIState.MEMBER_NO_PROJECT);
        }else if (members==null){
            jsonObject.put("state",APIState.MEMBER_NO_MEMBER);
        }else {
            for (int i=0;i<members.size();i++){
                ShowMember showMember=new ShowMember();
                User userInDB=members.get(i).getUser();
                showMember.setName(userInDB.getName());
                showMember.setNickName(userInDB.getNickName());
                showMember.setContribution(members.get(i).getContribution());
                System.out.println(members.get(i).getUser().getName()+"  contribution="+members.get(i).getContribution());
                showMembers.add(showMember);
            }
            jsonObject.put("data",showMembers);
            jsonObject.put("state",APIState.MEMBER_SHOW_RIGHT);
        }
        return jsonObject;
    }

    @GetMapping("/new")
    public Object newMember(@RequestHeader(value = "token") String token){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else {
            Member member=new Member();
            member.setContribution(3);
            jsonObject.put("member",member);
        }
        return jsonObject;
    }
}
