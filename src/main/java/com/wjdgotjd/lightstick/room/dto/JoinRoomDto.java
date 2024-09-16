package com.wjdgotjd.lightstick.room.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class JoinRoomDto {
    private String code;
    private UUID socketId;
}
