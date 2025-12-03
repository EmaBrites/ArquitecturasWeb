package com.exa.chatllm.service;

import com.exa.chatllm.client.AccountClient;
import com.exa.chatllm.client.GroqClient;
import com.exa.chatllm.client.TripClient;
import com.exa.chatllm.dto.AccountDTO;
import com.exa.chatllm.dto.ChatRequestDTO;
import com.exa.chatllm.dto.ChatResponseDTO;
import com.exa.chatllm.dto.FunctionDTO;
import com.exa.chatllm.dto.GroqRequestDTO;
import com.exa.chatllm.dto.MessageDTO;
import com.exa.chatllm.dto.ToolDTO;
import com.exa.chatllm.dto.TripDTO;
import com.exa.chatllm.enums.AccountTypeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    public static final String SYS_PROMPT = "Sos un sistema de alquiler de monopatines electrónicos en distintas paradas de una ciudad capital. Solo usa la informacion de las tools para contestar.";
    private final GroqClient groqClient;
    private final TripClient tripClient;
    private final AccountClient accountClient;
    @Value("${groq.model}")
    private String model;

    public ChatService(GroqClient groqClient, TripClient tripClient, AccountClient accountClient) {
        this.groqClient = groqClient;
        this.tripClient = tripClient;
        this.accountClient = accountClient;
    }

    public ChatResponseDTO procesarConsulta(ChatRequestDTO requestDTO) throws IllegalArgumentException, IllegalStateException {
        AccountDTO accountDTO = accountClient.getAccountById(requestDTO.accountId()).getBody();
        if (!accountDTO.getAccountType().equals(AccountTypeEnum.PREMIUM)) {
            throw new IllegalArgumentException("El servicio de chat solo está disponible para cuentas PREMIUM");
        }
        String prompt = requestDTO.prompt();
        GroqRequestDTO groqRequestDTO = getGroqRequestDTO(prompt);
        String requestPrompt = new ObjectMapper().writeValueAsString(groqRequestDTO);
        String response = groqClient.getChatCompletion(requestPrompt);
        ChatResponseDTO finalResponse = checkResponse(response, requestPrompt);
        if (finalResponse != null) return finalResponse;
        throw new IllegalStateException("No se pudo procesar la consulta");
    }

    private GroqRequestDTO getGroqRequestDTO(String prompt) {
        List<MessageDTO> messages = List.of(
                new MessageDTO(MessageRole.system.name(), SYS_PROMPT),
                new MessageDTO(MessageRole.user.name(), prompt)
        );
        List<FunctionDTO> functions = List.of(
                new FunctionDTO("get_trips", "Get all trips", new HashMap<>())
        );
        List<ToolDTO> tools = new ArrayList<>();
        functions.forEach(functionDTO -> tools.add(new ToolDTO(functionDTO)));
        return new GroqRequestDTO(model, messages, tools);
    }

    private ChatResponseDTO checkResponse(String response, String requestPrompt) {
        if (response != null && !response.isBlank()) {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> groqResponseMap = objectMapper.readValue(response, Map.class);
            List<Object> choicesMap = (List<Object>) groqResponseMap.get("choices");
            for (Object choice : choicesMap) {
                Map<String, Object> choiceMap = (Map<String, Object>) choice;
                Map<String, Object> messageMap = (Map<String, Object>) choiceMap.get("message");
                if (messageMap.containsKey("tool_calls")) {
                    List<Object> toolCalls = (List<Object>) messageMap.get("tool_calls");
                    List<Object> toolResponses = new ArrayList<>();
                    for (Object toolCall : toolCalls) {
                        Map<String, Object> toolCallMap = (Map<String, Object>) toolCall;
                        String id = (String) toolCallMap.get("id");
                        Map<String, Object> functionMap = (Map<String, Object>) toolCallMap.get("function");
                        String toolName = (String) functionMap.get("name");
                        String toolParameters = (String) functionMap.get("arguments");
                        Map<String, Object> toolResponseMap = clientToolCall(id, toolName, toolParameters);
                        toolResponses.add(toolResponseMap);
                    }
                    Map<String, Object> requestMap = new ObjectMapper().readValue(requestPrompt, Map.class);
                    List<Object> requestMessages = (List<Object>) requestMap.get("messages");
                    requestMessages.add(messageMap);
                    requestMessages.addAll(toolResponses);
                    requestMap.put("messages", requestMessages);
                    requestPrompt = new ObjectMapper().writeValueAsString(requestMap);
                    response = groqClient.getChatCompletion(requestPrompt);
                    return checkResponse(response, requestPrompt);
                } else if (choiceMap.get("finish_reason").equals("stop")) {
                    String finalResponse = (String) messageMap.get("content");
                    return new ChatResponseDTO(finalResponse);
                }
            }

        }
        return null;
    }

    private Map<String, Object> clientToolCall(String id, String toolName, String toolParameters) {
        switch (toolName) {
            case "get_trips" -> {
                //Llamar al TripClient para obtener los trips
                List<TripDTO> trips = tripClient.getTrips().getBody();
                //Convertir la respuesta a MessageDTO
                return Map.of(
                        "role", MessageRole.tool.name(),
                        "tool_call_id", id,
                        "name", toolName,
                        "content", String.valueOf(trips)
                );
            }
            default -> throw new IllegalArgumentException("Unknown tool: " + toolName);
        }
    }

    private enum MessageRole {
        system,
        user,
        assistant,
        tool
    }

}
