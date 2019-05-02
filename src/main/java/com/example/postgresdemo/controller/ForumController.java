package com.example.postgresdemo.controller;

import com.example.postgresdemo.model.Forum;
import com.example.postgresdemo.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ForumController {

    @Autowired
    private ForumRepository forumRepository;

    @GetMapping("/api/v1/forums")
    public Page<Forum> getForums(Pageable pageable) {
        return forumRepository.findAll(pageable);
    }


    @PostMapping("/api/v1/forums")
    public Forum createForum(@Valid @RequestBody Forum forum) {
        return forumRepository.save(forum);
    }
}