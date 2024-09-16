package com.wjdgotjd.lightstick.light.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.wjdgotjd.lightstick.light.dto.SetLightColorDto;
import com.wjdgotjd.lightstick.light.dto.SetLightOnDto;
import com.wjdgotjd.lightstick.light.dto.SetLightStateDto;
import com.wjdgotjd.lightstick.room.dto.LeaveRoomDto;
import com.wjdgotjd.lightstick.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LightService {

    private final RoomService roomService;

    private final SocketIOServer server;

    public void setColor(SocketIOClient client, SetLightColorDto dto) {
        if (dto.getCode().isEmpty()) return;

        if (!roomService.existsByCode(dto.getCode())) {
            return;
        }

        if (!client.getSessionId().equals(roomService.getOwner(dto.getCode()))) {
            return;
        }

        server.getRoomOperations(dto.getCode()).sendEvent("setLightColor", dto);
    }

    public void setOn(SocketIOClient client, SetLightOnDto dto) {
        if (dto.getCode().isEmpty()) return;

        if (!roomService.existsByCode(dto.getCode())) {
            return;
        }

        if (!client.getSessionId().equals(roomService.getOwner(dto.getCode()))) {
            return;
        }

        server.getRoomOperations(dto.getCode()).sendEvent("setLightOn", dto);
    }

    public void setState(SocketIOClient client, SetLightStateDto dto) {
        if (dto.getCode().isEmpty()) return;

        if (!roomService.existsByCode(dto.getCode())) {
            return;
        }

        if (client.getSessionId() != roomService.getOwner(dto.getCode())) {
            return;
        }

        server.getRoomOperations(dto.getCode()).sendEvent("setLightState", dto);
    }

    public void leaveRoom(SocketIOClient client, LeaveRoomDto dto) {
        if (!roomService.existsByCode(dto.getCode())) {
            throw new IllegalArgumentException("존재하지 않는 방입니다.");
        }

        client.leaveRoom(dto.getCode());
    }
}
