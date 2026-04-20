package com.prashant.AI_interview.dto;

public class InterviewResponse {

    private String question;
    private String feedback;
    private int score;

    public InterviewResponse() {
    }

    public InterviewResponse(String question, String feedback, int score) {
        this.question = question;
        this.feedback = feedback;
        this.score = score;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}