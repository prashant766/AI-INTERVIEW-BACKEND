package com.prashant.AI_interview.dto;

public class InterviewRequest {

    private String role;
    private String question;
    private String answer;
    private int questionNumber;

    public InterviewRequest() {
    }

    public InterviewRequest(String role, String question, String answer, int questionNumber) {
        this.role = role;
        this.question = question;
        this.answer = answer;
        this.questionNumber = questionNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }
}
