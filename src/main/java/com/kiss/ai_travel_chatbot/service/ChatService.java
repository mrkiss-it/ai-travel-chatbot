package com.kiss.ai_travel_chatbot.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class ChatService {
    private final String url = "https://openrouter.ai/api/v1/chat/completions";
    
    public String askLLM (String message) {
        String apiKey = "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";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = "{\n" +
                "  \"model\": \"openrouter/free\",\n" +
                "  \"messages\": [{\"role\":\"user\",\"content\":\"" + message + "\"}]\n" +
                "}";

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        System.out.println(headers);
        System.out.println(body);

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);  // 5s connect
        factory.setReadTimeout(15000);    // 15s chờ response
        RestTemplate restTemplate = new RestTemplate(factory);
        
        long start = System.currentTimeMillis();
        String response = restTemplate.postForObject(url, request, String.class);
        System.out.println(response);
        long end = System.currentTimeMillis();
        System.out.println("TIME: " + (end - start) + "ms");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);

        String text = root
                .path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText();

        return text;
    }
}
