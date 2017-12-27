package com.toplyh.server.service.websoket;

import com.toplyh.server.model.entity.project.member.Member;
import com.toplyh.server.model.repository.MemberRepository;
import com.toplyh.server.model.websocket.data.MessageMeeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by 我 on 2017/12/20.
 */
@Service
public class SocketService {

    private SimpMessagingTemplate simpMessagingTemplate;
    private MemberRepository memberRepository;

    @Autowired
    public SocketService(SimpMessagingTemplate simpMessagingTemplate,
                         MemberRepository memberRepository){
        this.simpMessagingTemplate=simpMessagingTemplate;
        this.memberRepository=memberRepository;
    }

    public void sendMeetingMessage(MessageMeeting message){

        System.out.println("Start sendMeetingMessage"+new Date());
        List<Member> members=memberRepository.findByProjectId(message.getProjectId());

    }

}
