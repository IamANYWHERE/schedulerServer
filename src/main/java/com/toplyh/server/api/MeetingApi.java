package com.toplyh.server.api;

import com.alibaba.fastjson.JSONObject;
import com.toplyh.server.api.state.APIState;
import com.toplyh.server.model.entity.User;
import com.toplyh.server.model.entity.project.Project;
import com.toplyh.server.model.entity.project.meeting.Meeting;
import com.toplyh.server.model.entity.project.member.Member;
import com.toplyh.server.model.json.data.AddMeeting;
import com.toplyh.server.model.json.data.MetAddMem;
import com.toplyh.server.service.normal.AuthenticationService;
import com.toplyh.server.service.normal.MeetingService;
import com.toplyh.server.service.normal.MemberService;
import com.toplyh.server.service.normal.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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

    @Autowired
    public MeetingApi(AuthenticationService authenticationService,
                      MeetingService meetingService,
                      ProjectService projectService,
                      MemberService memberService){
        this.authenticationService=authenticationService;
        this.meetingService=meetingService;
        this.projectService=projectService;
        this.memberService=memberService;
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
            jsonObject.put("state", APIState.AUTHENTICATION_TOKEN_ERROR);
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

    @GetMapping("/show")
    public Object showMeeting(@RequestHeader(value = "token") String token,
                              @RequestParam(value = "projectId") Integer id){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        List<Meeting> meetings=meetingService.findByProjectId(id);
        jsonObject.put("meetings",meetings);
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else if (!projectService.exists(id)){
            jsonObject.put("state",APIState.MEETING_NO_PROJECT);
        }else if (meetings==null){
            jsonObject.put("state",APIState.MEETING_NO_MEETING);
        }else {
            jsonObject.put("state",APIState.MEETING_SHOW_RIGHT);
        }
        return jsonObject;
    }


    @GetMapping("/new")
    public Object newMeeting(@RequestHeader(value = "token") String token){
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else {
            Project project=projectService.findById(2);
            Meeting meeting=new Meeting();
            meeting.setName("开会");
            meeting.setDate(new Date());
            meeting.setProject(project);
            jsonObject.put("meeting",meeting);
        }
        return jsonObject;
    }
}
