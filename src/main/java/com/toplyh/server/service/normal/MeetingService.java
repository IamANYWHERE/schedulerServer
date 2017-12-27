package com.toplyh.server.service.normal;

import com.toplyh.server.model.entity.project.meeting.Meeting;
import com.toplyh.server.model.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 我 on 2017/11/30.
 */
@Service
public class MeetingService {

    private MeetingRepository meetingRepository;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository){
        this.meetingRepository=meetingRepository;
    }

    public Meeting add(Meeting meeting){
        meetingRepository.save(meeting);
        return meetingRepository.findOne(meeting.getId());
    }

    public Meeting findById(Integer id){
        return meetingRepository.findOne(id);
    }

    public List<Meeting> findByProjectId(Integer id){
        return meetingRepository.findByProjectId(id);
    }

    public Boolean exists(Integer id){
        return meetingRepository.exists(id);
    }

    public void update(Meeting meeting){
        if (meetingRepository.exists(meeting.getId())){
            meetingRepository.save(meeting);
        }
    }
}
