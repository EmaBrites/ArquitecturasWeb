package com.exa.chatllm.controller;

import com.exa.chatllm.dto.ChatRequestDTO;
import com.exa.chatllm.dto.ChatResponseDTO;
import com.exa.chatllm.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    @Operation(summary = "Endpoint para procesar consultar en lenguaje natural sobre distintos servicios utilizando LLM.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta procesada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<ChatResponseDTO> procesarConsulta(@RequestBody ChatRequestDTO chatRequestDTO) {
        try {
            return ResponseEntity.ok(chatService.procesarConsulta(chatRequestDTO));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
