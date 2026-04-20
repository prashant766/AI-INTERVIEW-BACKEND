package com.prashant.AI_interview.service;

import com.prashant.AI_interview.dto.InterviewRequest;
import com.prashant.AI_interview.dto.InterviewResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterviewService {

    @Autowired
    private AIService aiService;

    public InterviewResponse processInterview(InterviewRequest request) {

        String technology = request.getRole();
        String answer = request.getAnswer();

        String prompt;

        // FIRST QUESTION
        if (answer == null || answer.trim().isEmpty()) {

            prompt = """
You are a strict technical interviewer for %s.

Start with a BASIC level question (very beginner).

IMPORTANT:
Return ONLY valid JSON.

FORMAT:
{
  "question": "your question",
  "score": 0,
  "feedback": "N/A"
}
""".formatted(technology);

        } else {

            prompt = """
You are a strict technical interviewer for %s.

Candidate answer:
"%s"


You MUST follow these rules:

- Ask only questions related to the given technology
- DO NOT switch language
- First 3 questions = BASIC
- Next 3 = MEDIUM
- Next 4 = HARD

IMPORTANT:
Return ONLY valid JSON.

CRITICAL:
You MUST return ONLY valid JSON.

NO explanation.
NO text outside JSON.

FORMAT:
{
  "question": "next question strictly about the given technology",
  "score": 7,
  "feedback": "short feedback"
}
""".formatted(technology, answer);

        }

        JsonNode aiResponse;

        try {
            aiResponse = aiService.askAI(prompt);
        } catch (Exception e) {
            return new InterviewResponse(
                    "AI service temporarily unavailable. Please try again in a moment.",
                    e.getMessage(),
                    0
            );
        }

        String question = aiResponse.get("question").asText();
        String feedback = aiResponse.get("feedback").asText();
        int score = aiResponse.get("score").asInt();

        return new InterviewResponse(question, feedback, score);

    }
}