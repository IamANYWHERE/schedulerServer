package com.toplyh.server.service.normal;

import com.toplyh.server.model.entity.project.member.Member;
import com.toplyh.server.model.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 我 on 2017/11/29.
 */
@Service
public class MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }

    public Member add(Member member){
        memberRepository.save(member);
        return memberRepository.findOne(member.getId());
    }

    public Member findById(Integer id){
        return memberRepository.findOne(id);
    }

    public List<Member> findByProjectId(Integer id){
        return memberRepository.findByProjectId(id);
    }

    public List<Member> findByUserId(Integer id){
        return memberRepository.findByUserId(id);
    }

    public Member findByProjectIdAndUserId(Integer projectId,Integer userId){
        return memberRepository.findByProjectIdAndUserId(projectId,userId);
    }

    public Boolean exists(Integer id){
        return memberRepository.exists(id);
    }
}
