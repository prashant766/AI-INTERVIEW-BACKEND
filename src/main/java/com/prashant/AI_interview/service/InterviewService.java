package com.prashant.AI_interview.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.prashant.AI_interview.dto.InterviewRequest;
import com.prashant.AI_interview.dto.InterviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterviewService {

    @Autowired
    private AIService aiService;

    @Autowired
    private QuestionService questionService;

    public InterviewResponse processInterview(InterviewRequest request) {

        String role = request.getRole();
        String answer = request.getAnswer();
        String currentQuestion = request.getQuestion();
        int questionNumber = request.getQuestionNumber();

        // FIRST QUESTION
        if (answer == null || answer.trim().isEmpty()) {

            String firstQuestion = questionService.getQuestion(role, questionNumber);

            return new InterviewResponse(
                    firstQuestion,
                    "N/A",
                    0
            );
        }

        // AI ONLY EVALUATES
        String prompt = """
You are a strict technical interviewer.

Question:
%s

Candidate Answer:
%s

Evaluate the answer.

Rules:
- Give score out of 10
- Give short feedback
- Be strict

Return ONLY valid JSON.

FORMAT:
{
  "score": 7,
  "feedback": "Good basic understanding but missing details."
}
""".formatted(currentQuestion, answer);

        JsonNode aiResponse;

        try {
            aiResponse = aiService.askAI(prompt);
        } catch (Exception e) {

            return new InterviewResponse(
                    "Error",
                    "AI evaluation failed",
                    0
            );
        }

        int score = aiResponse.get("score").asInt();
        String feedback = aiResponse.get("feedback").asText();

        // NEXT QUESTION
        String nextQuestion = questionService.getQuestion(role, questionNumber + 1);

        return new InterviewResponse(
                nextQuestion,
                feedback,
                score
        );
    }
}
