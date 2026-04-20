package com.prashant.AI_interview.Controller;

import com.prashant.AI_interview.dto.InterviewRequest;
import com.prashant.AI_interview.dto.InterviewResponse;
import com.prashant.AI_interview.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/interview")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @PostMapping("/start")
    public InterviewResponse startInterview(@RequestBody InterviewRequest request) {
        return interviewService.processInterview(request);
    }
    @GetMapping("/health")
    public String health() {
        return "Backend is running";
    }
}


