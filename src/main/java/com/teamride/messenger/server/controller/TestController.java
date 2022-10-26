package com.teamride.messenger.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamride.messenger.server.dto.AdminDTO;
import com.teamride.messenger.server.mapper.AdminMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {

	@Autowired
	AdminMapper  adminMapper;
	
    @GetMapping("/")
    public String test() {
        return "hi";
    }
    
    @GetMapping("test")
    public void mybatisTest() {
    }
}
