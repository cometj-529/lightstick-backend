package com.wjdgotjd.lightstick.room.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.wjdgotjd.lightstick.room.dto.CreateRoomDto;
import com.wjdgotjd.lightstick.room.dto.JoinRoomDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoomService {

    private final SocketIOServer server;
    @Getter
    private final Map<String, UUID> rooms = new HashMap<>();

    public boolean existsByCode(String code) {
        return rooms.containsKey(code);
    }

    public UUID getOwner(String code) {
        return rooms.get(code);
    }

    public void create(CreateRoomDto dto) {
        if (this.existsByCode(dto.getCode())) {
            throw new IllegalArgumentException("이미 존재하는 방입니다.");
        }

        SocketIOClient client = server.getClient(dto.getSocketId());

        if (client == null) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }

        rooms.put(dto.getCode(), dto.getSocketId());
        client.joinRoom(dto.getCode());
    }

    public void join(JoinRoomDto dto) {
        if (!this.existsByCode(dto.getCode())) {
            throw new IllegalArgumentException("존재하지 않는 방입니다.");
        }

        SocketIOClient client = server.getClient(dto.getSocketId());

        if (client == null) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }

        client.joinRoom(dto.getCode());
    }

    public void delete(SocketIOClient client, String code) {
        if (!this.existsByCode(code)) {
            throw new IllegalArgumentException("존재하지 않는 방입니다.");
        }

        if (!this.getOwner(code).equals(client.getSessionId())) {
            throw new IllegalArgumentException("자신의 방만 삭제할 수 있습니다.");
        }

        rooms.remove(code);
    }
}
