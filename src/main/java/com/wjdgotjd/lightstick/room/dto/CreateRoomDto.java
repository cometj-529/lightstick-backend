package com.wjdgotjd.lightstick.room.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateRoomDto {
    private String code;
    private UUID socketId;
}
