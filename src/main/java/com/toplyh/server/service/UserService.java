package com.toplyh.server.service;

import com.toplyh.server.model.entity.User;
import com.toplyh.server.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 我 on 2017/11/21.
 */
@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    private String passwordToHash(String password){
        try{
            MessageDigest digest=MessageDigest.getInstance("SHA-256");
            digest.update(password.getBytes());
            byte[] src=digest.digest();
            StringBuffer sb=new StringBuffer();
            //字节数组转16进制字符串
            for (byte aSrc:src){
                String s=Integer.toHexString(aSrc&0xff);
                if (s.length()<2){
                    sb.append('0');
                }
                sb.append(s);
            }
            return sb.toString();
        }catch(NoSuchAlgorithmException ignore){
            ignore.printStackTrace();
        }
        return null;
    }

    public boolean comparePassword(User user,User userInDataBase){
        return passwordToHash(user.getPassword()).equals(userInDataBase.getPassword());
    }

    public User add(User user){
        String passwordHash=passwordToHash(user.getPassword());
        user.setPassword(passwordHash);
        userRepository.save(user);
        return userRepository.findOne(user.getId());
    }

    public User findById(int id){
        return userRepository.findOne(id);
    }

    public User findByName(String name){
        return userRepository.findByName(name);
    }

    public int updateTokenById(Integer id,String token){
        return userRepository.updateTokenById(id,token);
    }
}
