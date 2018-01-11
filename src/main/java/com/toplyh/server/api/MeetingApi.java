package com.toplyh.server.api;

import com.alibaba.fastjson.JSONObject;
import com.toplyh.server.api.state.APIState;
import com.toplyh.server.model.entity.User;
import com.toplyh.server.model.entity.project.Project;
import com.toplyh.server.model.entity.project.meeting.Meeting;
import com.toplyh.server.model.entity.project.member.Member;
import com.toplyh.server.model.json.data.AddMeeting;
import com.toplyh.server.model.json.data.MetAndMem;
import com.toplyh.server.model.json.data.MetAddMem;
import com.toplyh.server.service.normal.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by 我 on 2017/11/30.
 */
@RestController
@RequestMapping("/api/meeting")
public class MeetingApi {

    private AuthenticationService authenticationService;
    private MeetingService meetingService;
    private ProjectService projectService;
    private MemberService memberService;
    private UserService userService;

    @Autowired
    public MeetingApi(AuthenticationService authenticationService,
                      MeetingService meetingService,
                      ProjectService projectService,
                      MemberService memberService,
                      UserService userService){
        this.authenticationService=authenticationService;
        this.meetingService=meetingService;
        this.projectService=projectService;
        this.memberService=memberService;
        this.userService=userService;
    }

    @PostMapping("/addMember")
    public Object addMember(@RequestHeader(value ="token") String token,
                            @RequestBody MetAddMem metAddMem){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        Meeting meeting=meetingService.findById(metAddMem.getMeetingId());
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else if (meeting==null){
            jsonObject.put("state",APIState.MEETING_NO_MEETING);
        }else {
            for (MetAddMem.MembersBean bean:
                 metAddMem.getMembers()) {
                Member m=memberService.findById(bean.getMemberId());
                if (m!=null){
                    meeting.getMembers().add(m);
                }
            }
            meetingService.update(meeting);
            jsonObject.put("state",APIState.MEETING_ADD_MEMBER_RIGHT);
        }
        return jsonObject;
    }

    @PostMapping("/add")
    public Object addMeeting(@RequestHeader(value = "token") String token,
                             @RequestBody AddMeeting aMeeting){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        Project project=projectService.findById(aMeeting.getProjectId());
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else if (project==null){
            jsonObject.put("state",APIState.MEETING_NO_PROJECT);
        }else {
            jsonObject.put("state",APIState.MEETING_ADD_RIGHT);
            Meeting meeting=new Meeting();
            meeting.setProject(project);
            meeting.setDate(aMeeting.getMeeting().getDate());
            meeting.setName(aMeeting.getMeeting().getName());
            meetingService.add(meeting);
        }
        return jsonObject;
    }

    @PostMapping("/addMeetingAndMember")
    public Object addMeetingAndMember(@RequestHeader(value = "token") String token,
                                      @RequestBody MetAndMem metAndMem){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else if (!projectService.exists(metAndMem.getProjectId())){
            jsonObject.put("state",APIState.MEETING_NO_PROJECT);
        }else {
            Meeting meeting=new Meeting();
            meeting.setName(metAndMem.getName());
            meeting.setDate(metAndMem.getDate());
            meeting.setProject(projectService.findById(metAndMem.getProjectId()));
            List<String> members= metAndMem.getMembers();
            for (String name:
                 members) {
                User muser=userService.findByName(name);
                if (muser==null){
                    continue;
                }
                Member member=memberService.findByProjectIdAndUserId(metAndMem.getProjectId(),muser.getId());
                if (member==null){
                    continue;
                }
                meeting.getMembers().add(member);
            }
            meetingService.add(meeting);
            jsonObject.put("state",APIState.MEETING_AND_MEMBER_ADD_RIGHT);
        }
        return jsonObject;
    }

    @GetMapping("/show")
    public Object showMeeting(@RequestHeader(value = "token") String token,
                              @RequestParam(value = "projectId") Integer id){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        List<Meeting> meetings=meetingService.findByProjectId(id);
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else if (!projectService.exists(id)){
            jsonObject.put("state",APIState.MEETING_NO_PROJECT);
        }else if (meetings==null){
            jsonObject.put("state",APIState.MEETING_NO_MEETING);
        }else {
            jsonObject.put("state",APIState.MEETING_SHOW_RIGHT);
            List<MetAndMem> list=new ArrayList<>();
            for (Meeting m :
                    meetings) {
                MetAndMem mm=new MetAndMem();
                mm.setName(m.getName());
                mm.setDate(m.getDate());
                mm.setProjectId(m.getProject().getId());
                mm.setMeetingId(m.getId());
                mm.setMembers(new ArrayList<>());
                Iterator<Member> mit=m.getMembers().iterator();
                while (mit.hasNext()){
                    mm.getMembers().add(mit.next().getUser().getName());
                }
                list.add(mm);
            }
            jsonObject.put("data",list);
        }
        return jsonObject;
    }


    @GetMapping("/new")
    public Object newMeeting(@RequestHeader(value = "token") String token){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        /*if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else {
            Project project=projectService.findById(2);
            Meeting meeting=new Meeting();
            meeting.setName("开会");
            meeting.setDate(new Date());
            meeting.setProject(project);
            jsonObject.put("meeting",meeting);
        }*/
        MetAndMem add=new MetAndMem();
        add.setDate(new Date());
        add.setName("adsadsa");
        add.setProjectId(3);
        add.setMembers(new ArrayList<>());
        add.getMembers().add("asdsa");
        add.getMembers().add("rgfds");

        return add;
    }
}
