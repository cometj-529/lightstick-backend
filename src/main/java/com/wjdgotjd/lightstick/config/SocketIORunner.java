package com.wjdgotjd.lightstick.config;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class SocketIORunner {

    private final SocketIOServer server;

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            server.start();
            Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
        };
    }
}
