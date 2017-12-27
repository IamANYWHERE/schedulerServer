package com.toplyh.server.api;

import com.alibaba.fastjson.JSONObject;
import com.toplyh.server.api.state.APIState;
import com.toplyh.server.model.entity.skill.Skill;
import com.toplyh.server.model.entity.User;
import com.toplyh.server.service.normal.AuthenticationService;
import com.toplyh.server.service.normal.SkillService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 我 on 2017/11/28.
 */
@RestController
@RequestMapping("/api/skill")
public class SkillApi {

    private AuthenticationService authenticationService;
    private SkillService skillService;

    public SkillApi(AuthenticationService authenticationService,
                    SkillService skillService){
        this.authenticationService=authenticationService;
        this.skillService=skillService;
    }

    @GetMapping("/add")
    public Object addSkill(@RequestHeader(value = "token") String token,
                           @RequestParam(value = "skill",required = true) String skill){
        JSONObject jsonObject=new JSONObject();
        User user=authenticationService.authenticateToken(token);
        if (user==null){
            jsonObject.put("state", APIState.AUTHENTICATION_TOKEN_ERROR);
        }else {
            Skill skillInClient = new Skill();
            skillInClient.setDescription(skill);
            skillInClient.setUser(user);
            System.out.println(skill+":::");
            skillService.add(skillInClient);
            jsonObject.put("state", APIState.SKILL_ADD_RIGHT);
        }
        return jsonObject;
    }

    @GetMapping("/show")
    public Object showSkills(@RequestHeader(value = "token") String token){
        JSONObject jsonObject=new JSONObject();
        User user=authenticationService.authenticateToken(token);
        List<Skill> skills=null;
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
            jsonObject.put("skills",skills);
        }else {
            skills = skillService.findByUserName(user.getName());
            jsonObject.put("state", APIState.SKILL_SHOW_RIGHT);
            jsonObject.put("skills", skills);
        }
        return jsonObject;
    }
}
