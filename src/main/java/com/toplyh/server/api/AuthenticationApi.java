package com.toplyh.server.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.toplyh.server.api.state.APIState;
import com.toplyh.server.model.entity.User;
import com.toplyh.server.model.entity.project.Project;
import com.toplyh.server.model.entity.project.member.Member;
import com.toplyh.server.model.json.data.Count;
import com.toplyh.server.model.json.data.SimpProject;
import com.toplyh.server.service.normal.AuthenticationService;
import com.toplyh.server.service.normal.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by 我 on 2017/11/22.
 */
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationApi {
    private AuthenticationService authenticationService;
    private UserService userService;

    @Autowired
    public AuthenticationApi(AuthenticationService authenticationService, UserService userService){
        this.authenticationService=authenticationService;
        this.userService=userService;
    }

    @PostMapping("")
    public Object login(@RequestBody Count count){
        User user=new User();
        user.setName(count.getName());
        user.setPassword(count.getPassword());
        User userInDataBase=userService.findByName(user.getName());
        JSONObject jsonObject;
        if (userInDataBase==null){
            jsonObject=login("",APIState.LOGIN_NAME_ERROR);
        }else if (!userService.comparePassword(user,userInDataBase)){
            jsonObject=login("",APIState.LOGIN_PASSWORD_ERROR);
        }else{
            String token=authenticationService.getToken(userInDataBase);
            String signature=authenticationService.getSignature(token);
            userInDataBase.setSignature(signature);
            userService.update(userInDataBase);
            jsonObject=login(token,APIState.LOGIN_RIGHT);

            //测试所用
            JWT jwt=JWT.decode(token);
            System.out.println(jwt.getAlgorithm()+":"+jwt.getContentType()+":"+jwt.getId()+":"+
            jwt.getSignature()+":"+jwt.getIssuer()+":"+jwt.getKeyId()+":"+jwt.getSubject()+":"+
            jwt.getType()+":"+jwt.getAudience());
        }
        return jsonObject;
    }

    /**
     * 返回json结果
     * @param token
     * @param state  表示登录状态
     * @return
     */
    private JSONObject login(String token,int state){
        JSONObject jsonObject=new JSONObject();
        JSONObject jsonData=new JSONObject();
        /*JSONArray jsonArraySelf=new JSONArray();
        JSONArray jsonArrayOther=new JSONArray();

        if (user!=null) {

            Set<Project> projects = user.getProjects();
            Iterator<Project> pit = projects.iterator();

            Set<Member> members = user.getMembers();
            Iterator<Member> mit = members.iterator();
            List<Project> mprojects = new ArrayList<>();
            while (mit.hasNext()) {
                mprojects.add(mit.next().getProject());
            }

            for (int i = 0; i < mprojects.size(); i++) {
                SimpProject sp = new SimpProject(mprojects.get(i));
                jsonArrayOther.add(sp);
            }

            while (pit.hasNext()) {
                JSONObject jsonProject = new JSONObject();
                Project project = pit.next();
                SimpProject simpProject = new SimpProject(project);
                jsonArraySelf.add(simpProject);
            }
        }*/
        jsonData.fluentPut("token",token);
        jsonObject.put("state",state);
        jsonObject.fluentPut("data",jsonData);

        return jsonObject;
    }
}
