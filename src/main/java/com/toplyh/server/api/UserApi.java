package com.toplyh.server.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toplyh.server.api.state.APIState;
import com.toplyh.server.model.entity.User;
import com.toplyh.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
