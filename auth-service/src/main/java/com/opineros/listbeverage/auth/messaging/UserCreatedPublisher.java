package com.opineros.listbeverage.auth.messaging;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class UserCreatedPublisher {

    private final StreamBridge streamBridge;

    public UserCreatedPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    /**
     * Envía solo el userId al canal 'user-created-out-0'.
     */
    public void publishUserCreated(Long userId) {
        streamBridge.send("user-created-out-0", userId);
    }
}