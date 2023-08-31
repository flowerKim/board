package com.example.board2.controller;

import com.example.board2.dto.ResponseDto;
import com.example.board2.dto.SignInDto;
import com.example.board2.dto.SignInResponseDto;
import com.example.board2.dto.SignUpDto;
import com.example.board2.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/signUp")
    public ResponseDto<?> signUp(@RequestBody SignUpDto requestBody){
        System.out.println(requestBody.toString());
        ResponseDto<?> result = authService.signUp(requestBody);
        return result;
    }

    @PostMapping("/signIn")
    public ResponseDto<SignInResponseDto> signIn(@RequestBody SignInDto requestBody){
        System.out.println(requestBody.toString());
        ResponseDto<SignInResponseDto> result = (ResponseDto<SignInResponseDto>) authService.signIn(requestBody);
        return result;
    }

}
