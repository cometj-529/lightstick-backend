package com.wjdgotjd.lightstick.light.controller;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.wjdgotjd.lightstick.light.dto.SetLightOnDto;
import com.wjdgotjd.lightstick.light.dto.SetLightColorDto;
import com.wjdgotjd.lightstick.light.dto.SetLightStateDto;
import com.wjdgotjd.lightstick.light.service.LightService;
import com.wjdgotjd.lightstick.room.dto.LeaveRoomDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LightEventHandler {

    private final SocketIOServer server;
    private final LightService service;

    @PostConstruct
    private void init() {
        server.addListeners(this);
    }

    @OnConnect
    private void onConnect(SocketIOClient client) {
        log.info("connected: " + client.getSessionId());
    }

    @OnDisconnect
    private void onDisconnect(SocketIOClient client) {
        log.info("disconnected: " + client.getSessionId());
    }

    @OnEvent("setLightColor")
    public void onSetColor(SocketIOClient client, SetLightColorDto dto) {
        service.setColor(client, dto);
    }

    @OnEvent("setLightOn")
    public void onSetOn(SocketIOClient client, SetLightOnDto dto) {
        service.setOn(client, dto);
    }

    @OnEvent("setState")
    public void onSetState(SocketIOClient client, SetLightStateDto dto) {
        service.setState(client, dto);
    }

    @OnEvent("leaveRoom")
    public void onLeaveRoom(SocketIOClient client, LeaveRoomDto dto) {
        service.leaveRoom(client, dto);
    }

}
