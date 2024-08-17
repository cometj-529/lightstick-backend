package com.wjdgotjd.lightstick.config;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
