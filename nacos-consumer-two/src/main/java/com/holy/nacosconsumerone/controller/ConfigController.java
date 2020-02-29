package com.holy.nacosconsumerone.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${user.name}")
    private String username;

    @Value("${user.age}")
    private String age;

    @Value("${user.addr}")
    private String address;



    @RequestMapping("/get")
    public String get() {
        return "Hello " + username + " age :" + age + "addr:" + address;
    }
}