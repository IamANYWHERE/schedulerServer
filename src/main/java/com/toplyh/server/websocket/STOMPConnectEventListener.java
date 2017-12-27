package com.toplyh.server.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * Created by 我 on 2017/12/21.
 */
public class STOMPConnectEventListener implements ApplicationListener<SessionConnectEvent> {

    @Autowired
    SocketSessionRegistry socketSessionRegistry;


    @Override
    public void onApplicationEvent(SessionConnectEvent sessionConnectEvent) {
        StompHeaderAccessor sha=StompHeaderAccessor.wrap(sessionConnectEvent.getMessage());
        String agentId =sha.getNativeHeader("login").get(0);
        if (agentId.equals("username")){
            return;
        }
        String sessionId=sha.getSessionId();
        socketSessionRegistry.registerSessionId(agentId,sessionId);
    }
}
