package com.teamride.messenger.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {


    @GetMapping("/")
    public String test() {
        return "hi";
    }
    
    @GetMapping("test")
    public void mybatisTest() {
        
    }
}
