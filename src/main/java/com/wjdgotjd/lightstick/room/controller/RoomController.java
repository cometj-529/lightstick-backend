package com.wjdgotjd.lightstick.room.controller;

import com.wjdgotjd.lightstick.room.dto.JoinRoomDto;
import com.wjdgotjd.lightstick.room.dto.CreateRoomDto;
import com.wjdgotjd.lightstick.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService service;

    @PostMapping("")
    public void create(@RequestBody CreateRoomDto dto) {
        service.create(dto);
    }

    @PostMapping("/join")
    public void join(@RequestBody JoinRoomDto dto) {
        service.join(dto);
    }
}
