package com.toplyh.server.api;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.sun.javafx.fxml.expression.Expression;
import com.toplyh.server.api.state.APIState;
import com.toplyh.server.model.entity.User;
import com.toplyh.server.service.AuthenticationService;
import com.toplyh.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Object login(@RequestBody User user){
        User userInDataBase=userService.findByName(user.getName());
        JSONObject jsonObject=new JSONObject();
        if (userInDataBase==null){
            jsonObject.put("token","");
            jsonObject.put("state", APIState.LOGIN_NAME_ERROR);
        }else if (!userService.comparePassword(user,userInDataBase)){
            jsonObject.put("token","");
            jsonObject.put("state",APIState.LOGIN_PASSWORD_ERROR);
        }else{
            String token=authenticationService.getToken(userInDataBase);
            userService.updateTokenById(userInDataBase.getId(),token);
            jsonObject.put("token",token);
            jsonObject.put("state",APIState.LOGIN_RIGHT);
            JWT jwt=JWT.decode(token);
            System.out.println(jwt.getAlgorithm()+":"+jwt.getContentType()+":"+jwt.getId()+":"+
            jwt.getSignature()+":"+jwt.getIssuer()+":"+jwt.getKeyId()+":"+jwt.getSubject()+":"+
            jwt.getType()+":"+jwt.getAudience());
        }
        return jsonObject;
    }
}
