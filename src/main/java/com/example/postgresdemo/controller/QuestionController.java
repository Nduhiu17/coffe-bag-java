package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Question;
import com.example.postgresdemo.repository.ForumRepository;
import com.example.postgresdemo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ForumRepository forumRepository;


    @PostMapping("/api/v1/forums/{forumId}/questions")
    public Question addQuestion(@PathVariable Long forumId,
                            @Valid @RequestBody Question question) {
        return forumRepository.findById(forumId)
                .map(forum -> {
                    question.setForum(forum);
                    return questionRepository.save(question);
                }).orElseThrow(() -> new ResourceNotFoundException("Forum not found with id " + forumId));
    }



    @PutMapping("/api/v1/forums/{forumId}/questions/{questionId}")
    public Question updateQuestion(@PathVariable Long forumId,
                               @PathVariable Long questionId,
                               @Valid @RequestBody Question questionRequest) {
        if(!forumRepository.existsById(forumId)) {
            throw new ResourceNotFoundException("Forum not found with id " + forumId);
        }

        return questionRepository.findById(questionId)
                .map(question -> {
                    question.setTitle(questionRequest.getTitle());
                    question.setDescription(questionRequest.getDescription());
                    return questionRepository.save(question);
                }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }




    @GetMapping("/api/v1/forums/{forumId}/questions")
    public List<Question> getQuestionsByForumId(@PathVariable Long forumId) {
        return questionRepository.findByForumId(forumId);
    }

    @DeleteMapping("/api/v1/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        return questionRepository.findById(questionId)
                .map(question -> {
                    questionRepository.delete(question);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }
}