package com.toplyh.server.api;

import com.alibaba.fastjson.JSONObject;
import com.toplyh.server.api.state.APIState;
import com.toplyh.server.model.entity.User;
import com.toplyh.server.model.json.data.Count;
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

    @Autowired
    public UserApi(UserService userService){
        this.userService=userService;
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