package com.toplyh.server.api;

import com.alibaba.fastjson.JSONObject;
import com.toplyh.server.api.state.APIState;
import com.toplyh.server.model.entity.User;
import com.toplyh.server.model.entity.project.meeting.Meeting;
import com.toplyh.server.model.entity.project.member.Member;
import com.toplyh.server.model.websocket.data.Message;
import com.toplyh.server.model.websocket.data.MessageMeeting;
import com.toplyh.server.service.normal.*;
import com.toplyh.server.service.websoket.SocketService;
import com.toplyh.server.websocket.SocketSessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 我 on 2017/12/20.
 */
@RestController
@RequestMapping("/api/websocket")
public class SocketApi {

    private AuthenticationService authenticationService;
    private SocketService socketService;
    private MeetingService meetingService;
    private ProjectService projectService;
    private MemberService memberService;
    private UserService userService;
    private SocketSessionRegistry socketSessionRegistry;
    private SimpMessagingTemplate template;

    @Autowired
    public SocketApi(SocketService socketService,
                     AuthenticationService authenticationService,
                     MeetingService meetingService,
                     ProjectService projectService,
                     MemberService memberService,
                     UserService userService,
                     SocketSessionRegistry socketSessionRegistry,
                     SimpMessagingTemplate simpMessagingTemplate){
        this.socketService=socketService;
        this.authenticationService=authenticationService;
        this.meetingService=meetingService;
        this.projectService=projectService;
        this.memberService=memberService;
        this.userService=userService;
        this.socketSessionRegistry=socketSessionRegistry;
        this.template=simpMessagingTemplate;
    }


    /*@PostMapping("/msg/meeting")
    public Object addMeeting(@RequestHeader(value = "token") String token,
                           @RequestBody MessageMeeting message){
        System.out.println("Start websocket addmeeting"+new Date());
        User user=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        if (user==null){
            jsonObject.put("state", APIState.AUTHENTICATION_TOKEN_ERROR);
        }else {
            jsonObject.put("state",APIState.WEB_SOCKET_ADD_MEETING_RIGHT);
            Meeting meeting=new Meeting();
            meeting.setName(message.getDescription());
            meeting.setDate(message.getTime());
            meeting.setProject(projectService.findById(message.getProjectId()));
            Set<Member> members=new HashSet<>();
            String[] targets=(String[]) message.getTargets().toArray();
            for (String username : targets) {
                User u=userService.findByName(username);
                if (u==null) {
                    message.getTargets().remove(username);
                    break;
                }
                Member m=memberService.findByProjectIdAndUserId(message.getProjectId(),u.getId());
                if (m==null){
                    message.getTargets().remove(username);
                    break;
                }
                members.add(m);
            }
            meeting.setMembers(members);
            meetingService.add(meeting);
            String sessionId=socketSessionRegistry.getSessionIds(message.getOrigin()).stream().findFirst().get();
            template.convertAndSendToUser(sessionId,"/topic/greetings",new );
            System.out.println("End websocket addmeeting"+new Date());
        }
        return jsonObject;
    }*/



}
