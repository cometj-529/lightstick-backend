package com.wjdgotjd.lightstick.light.controller;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.wjdgotjd.lightstick.light.dto.JoinRoomDto;
import com.wjdgotjd.lightstick.light.dto.LightOnChangeDto;
import com.wjdgotjd.lightstick.light.dto.LightSetColorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class LightEventHandler {

    private final SocketIOServer server;
    private final Map<String, UUID> seats = new HashMap<>();

    public LightEventHandler(SocketIOServer server) {
        this.server = server;
        server.addListeners(this);
    }

    @OnConnect
    private void onConnect(SocketIOClient client) {
        log.info(client.getSessionId().toString());
        System.out.println("connected: " + client.getSessionId());
    }

    @OnDisconnect
    private void onDisconnect(SocketIOClient client) {
        System.out.println("disconnected: " + client.getSessionId());
    }

    @OnEvent("joinRoom")
    public void onJoinRoom(SocketIOClient client, JoinRoomDto dto) {
        log.info("onJoinRoom: " + client.getSessionId() + " " + dto.getCode());
        client.joinRoom(dto.getCode());
        seats.put(dto.getCode(), client.getSessionId());
    }

    @OnEvent("lightSetColor")
    public void onLightSetColor(SocketIOClient client, LightSetColorDto dto) {

        if (dto.getCode().isEmpty()) return;

        server.getRoomOperations(dto.getCode()).sendEvent("lightSetColor", dto);
    }

    @OnEvent("lightOnChange")
    public void lightOnChange(SocketIOClient client, LightOnChangeDto dto) {

        if (dto.getCode().isEmpty()) return;

        server.getRoomOperations(dto.getCode()).sendEvent("lightOnChange", dto);
    }

}
