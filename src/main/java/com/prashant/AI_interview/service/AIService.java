package com.prashant.AI_interview.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class AIService {

    @Value("${groq.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.groq.com/openai/v1")
            .defaultHeader("Content-Type", "application/json")
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode askAI(String prompt) {

        Map<String, Object> requestBody = Map.of(
                "model", "llama-3.1-8b-instant",
                "messages", List.of(

                      Map.of(
        "role", "system",
        "content", """
You are a technical interviewer.

Your job is ONLY to evaluate answers.

Rules:
- Give score out of 10
- Give short feedback
- Be strict
- Return ONLY valid JSON
- No extra text

FORMAT:
{
  "score": 7,
  "feedback": "Good understanding but missing some details."
}
"""
),

                        Map.of(
                                "role", "user",
                                "content", prompt
                        )

                ),
                "temperature", 0.5
        );

        String response;

        try {
            response = webClient.post()
                    .uri("/chat/completions")
                    .header("Authorization", "Bearer " + apiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Groq API call failed: " + e.getMessage());
        }

        try {
            JsonNode root = objectMapper.readTree(response);

            String content = root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

            // 🔥 Parse AI JSON output safely
            System.out.println("RAW AI RESPONSE: " + content);

            try {
                return objectMapper.readTree(content);
            } catch (Exception e) {

                int score = 0;

                if (content.contains("/10")) {
                    try {
                        String num = content.replaceAll(".*?(\\d+)/10.*", "$1");
                        score = Integer.parseInt(num);
                    } catch (Exception ignored) {}
                }

               return objectMapper.createObjectNode()
        .put("score", score)
        .put("feedback", content);
            }

        } catch (Exception e) {
            throw new RuntimeException("AI response parsing failed. Raw response issue.");
        }
    }
}
