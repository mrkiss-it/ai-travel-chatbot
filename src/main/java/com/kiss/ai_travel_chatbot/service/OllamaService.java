package com.kiss.ai_travel_chatbot.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class OllamaService {
    private static final String URL = "http://localhost:11434/api/generate";
    
    public String generate (String prompt) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            String body = """
                    {
                    "model": "gemma3:1b",
                    "prompt": "%s",
                    "stream": false
                    }
                    """.formatted(prompt);
            
            HttpEntity<String> entity = new HttpEntity<String>(body, headers);
            System.out.println(entity);

            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(5000);
            factory.setReadTimeout(30000);

            ResponseEntity<String> response = new RestTemplate(factory).postForEntity(URL, entity, String.class);
            System.out.println(response);
            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            String text = root.path("response").asText();

            return text;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
