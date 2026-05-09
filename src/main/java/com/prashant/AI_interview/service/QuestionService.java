package com.prashant.AI_interview.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    public String getQuestion(String role, int questionNumber) {

        role = role.toLowerCase();

        if (role.equals("java")) {

            List<String> questions = List.of(

                    // EASY
                    "Who developed Java?",
                    "What is JVM?",
                    "What is JDK?",
                    "What is System.out.println() used for?",

                    // MEDIUM
                    "What is inheritance in Java?",
                    "Difference between JDK and JRE?",
                    "What is polymorphism?",
                    "What is method overloading?",

                    // HARD
                    "What is multithreading?",
                    "Difference between HashMap and ConcurrentHashMap?"
            );

            if (questionNumber >= 1 && questionNumber <= questions.size()) {
                return questions.get(questionNumber - 1);
            }
        }

        return "No question available";
    }
}
