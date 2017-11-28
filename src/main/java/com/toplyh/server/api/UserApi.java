package com.toplyh.server.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toplyh.server.api.state.APIState;
import com.toplyh.server.model.entity.Skill;
import com.toplyh.server.model.entity.User;
import com.toplyh.server.model.repository.SkillRepository;
import com.toplyh.server.service.AuthenticationService;
import com.toplyh.server.service.SkillService;
import com.toplyh.server.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by 我 on 2017/11/21.
 */

@RestController
@RequestMapping("/api/user")
public class UserApi {

    private UserService userService;
    private AuthenticationService authenticationService;
    private SkillService skillService;

    @Autowired
    public UserApi(UserService userService,
                   AuthenticationService authenticationService,
                   SkillService skillService){
        this.userService=userService;
        this.authenticationService=authenticationService;
        this.skillService=skillService;
    }

    @PostMapping("")
    public Object add(@RequestBody User user){
        JSONObject jsonObject=new JSONObject();
        if (userService.findByName(user.getName())!=null){
            jsonObject.put("state", APIState.REGISTER_ERROR);
        }else {
            jsonObject.put("state", APIState.REGISTER_RIGHT);
            userService.add(user);
        }
        return jsonObject;
    }

    @GetMapping("/skills/add")
    public Object addSkill(@RequestHeader(value = "token") String token,
                           @RequestParam(value = "skill",required = true) String skill){
        System.out.println(token);
        JSONObject jsonObject=new JSONObject();
        User user=authenticationService.authenticateToken(token);
        if (user==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else {
            Skill skillInClient = new Skill();
            skillInClient.setDescription(skill);
            skillInClient.setUser(user);
            System.out.println(skill+":::");
            Skill skillInDatabase = skillService.add(skillInClient);
            jsonObject.put("state", APIState.SKILL_ADD_RIGHT);
        }
        return jsonObject;
    }

    @GetMapping("skills/show")
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