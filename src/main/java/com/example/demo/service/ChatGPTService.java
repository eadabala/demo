package com.example.demo.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.demo.dto.ChatGPTRequest;
import com.example.demo.dto.ChatGPTResponse;
import com.example.demo.dto.PromptRequest;

@Service
public class ChatGPTService {

    private final RestClient restClient;

    public ChatGPTService(RestClient restClient){
        this.restClient = restClient;
    }


    private String apiKey;


    private String model;

    public String getChatResponse(PromptRequest promptRequest){

        ChatGPTRequest chatGPTRequest = new ChatGPTRequest(
                model,
                List.of(new ChatGPTRequest.Message("user", promptRequest.prompt()))
        );

        ChatGPTResponse response = restClient.post()
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(chatGPTRequest)
                .retrieve()
                .body(ChatGPTResponse.class);

        return response.choices().get(0).message().content();

    }
}
