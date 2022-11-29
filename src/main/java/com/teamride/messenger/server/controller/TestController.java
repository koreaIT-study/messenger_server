package com.teamride.messenger.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamride.messenger.server.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {

	@Autowired
	UserMapper  adminMapper;
	
    @GetMapping("/")
    public String test() {
        return "hi";
    }
    
    @GetMapping("test")
    public void mybatisTest() {
        
    }
}
