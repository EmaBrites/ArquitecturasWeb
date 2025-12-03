package com.exa.chatllm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ChatLlmApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatLlmApplication.class, args);
    }

}
