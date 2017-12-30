package com.toplyh.server.api;

import com.alibaba.fastjson.JSONObject;
import com.toplyh.server.api.state.APIState;
import com.toplyh.server.model.entity.User;
import com.toplyh.server.model.json.data.Count;
import com.toplyh.server.model.json.data.MsgCount;
import com.toplyh.server.model.json.data.UpdateCount;
import com.toplyh.server.service.normal.AuthenticationService;
import com.toplyh.server.service.normal.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 我 on 2017/11/21.
 */

@RestController
@RequestMapping("/api/user")
public class UserApi {

    private UserService userService;

    private AuthenticationService authenticationService;

    @Autowired
    public UserApi(UserService userService,
                   AuthenticationService authenticationService){
        this.userService=userService;
        this.authenticationService=authenticationService;
    }

    @PostMapping("")
    public Object add(@RequestBody Count count){
        JSONObject jsonObject=new JSONObject();
        User user=new User();
        user.setName(count.getName());
        user.setPassword(count.getPassword());
        user.setNickName(count.getNickName());
        if (userService.findByName(user.getName())!=null){
            jsonObject.put("state", APIState.REGISTER_ERROR);
        }else {
            jsonObject.put("state", APIState.REGISTER_RIGHT);
            userService.add(user);
        }

        return jsonObject;

    }

    @GetMapping("/msg")
    public Object getMsg(@RequestHeader(value = "token") String token){
        User userInDB=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        if (userInDB==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else {
            MsgCount count=new MsgCount();
            count.setNickName(userInDB.getNickName());
            count.setName(userInDB.getName());
            jsonObject.put("state",APIState.GET_MSG_RIGHT);
            jsonObject.put("data",count);
        }
        return jsonObject;
    }

    @PostMapping("/update")
    public Object update(@RequestHeader(value = "token") String token,
                         @RequestBody UpdateCount updateCount){
        User userInDB=authenticationService.authenticateToken(token);
        JSONObject jsonObject=new JSONObject();
        User user=new User();
        user.setPassword(updateCount.getCurrentPassword());
        if (userInDB==null){
            jsonObject.put("state",APIState.AUTHENTICATION_TOKEN_ERROR);
        }else if (!userService.comparePassword(user,userInDB)){
            jsonObject.put("state",APIState.UPDATE_USER_PASSWORD_ERROR);
        }else {
            userInDB.setNickName(updateCount.getNickName());
            if (updateCount.getNewPassword()!=null&&!updateCount.getNewPassword().equals("")){
                userInDB.setPassword(updateCount.getNewPassword());
                userService.add(userInDB);
            }else {
                userService.update(userInDB);
            }
            jsonObject.put("state",APIState.UPDATE_USER_RIGHT);
        }
        return jsonObject;
    }

    @GetMapping("/new")
    public Object newUser(){
        JSONObject jsonObject=new JSONObject();
        User user=new User();
        user.setName("george");
        user.setPassword("123456");
        jsonObject.put("user",user);
        return jsonObject;
    }
}