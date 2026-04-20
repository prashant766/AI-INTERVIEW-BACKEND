package com.prashant.AI_interview.dto;

public class InterviewRequest {

    private String role;
    private String answer;

    public InterviewRequest() {
    }

    public InterviewRequest(String role, String answer) {
        this.role = role;
        this.answer = answer;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}