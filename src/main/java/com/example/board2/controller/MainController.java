package com.example.board2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//RestController ? Controller + ResponseBody = Data를 직접 보내겠다.
//RequestMapping(URL패턴) ? Request URL 의 패턴을 보고 해당하는 패턴의 클래스를 실행
@RestController
@RequestMapping("/")
public class MainController {
    @GetMapping("")
    public String hello() {return "Connect Successful";}
}
