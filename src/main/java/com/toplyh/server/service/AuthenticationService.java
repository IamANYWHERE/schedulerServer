package com.toplyh.server.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.toplyh.server.model.entity.User;
import com.toplyh.server.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * Created by 我 on 2017/11/22.
 */
@Service
public class AuthenticationService {

    private UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public String getToken(User user){
        String token="";
        try{
            token= JWT.create()
                    .withAudience(new String[]{user.getId()+"",System.currentTimeMillis()+""})
                    .sign(Algorithm.HMAC256(user.getPassword()));
        }catch ( UnsupportedEncodingException ignore){
            ignore.printStackTrace();
        }


        return token;
    }

    public String getSignature(String token){
        JWT jwt=JWT.decode(token);
        return jwt.getSignature();
    }

    public User authenticateToken(String token){
        JWT jwt=JWT.decode(token);
        User user=userRepository.findOne(Integer.valueOf(jwt.getAudience().get(0)));
        if (user==null||!jwt.getSignature().equals(user.getSignature())){
            return null;
        }
        else {
            return user;
        }
    }
}
