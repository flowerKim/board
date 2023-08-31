package com.example.board2.service;

import com.example.board2.dto.ResponseDto;
import com.example.board2.dto.SignInDto;
import com.example.board2.dto.SignInResponseDto;
import com.example.board2.dto.SignUpDto;
import com.example.board2.entity.UserEntity;
import com.example.board2.repository.UserRepository;
import com.example.board2.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseDto<?> signUp(SignUpDto dto) {
        String userEmail = dto.getUserEmail();
        String userPassword = dto.getUserPassword();
        String userPasswordCheck = dto.getUserPasswordCheck();

        //email 중복 확인
        //repository 접근할때는 try catch문으로 감싸기.
        try {
            if(userRepository.existsById(userEmail))
                return ResponseDto.setFailed("Existed Email!");
        }catch (Exception error){
            return ResponseDto.setFailed("Data Base Error!");
        }
        //비밀번호가 다르면 failed response 반환
        if(!userPassword.equals(userPasswordCheck))
            return ResponseDto.setFailed("password does not matched");

        //UserEntity 생성
        UserEntity userEntity = new UserEntity(dto);

        //비밀번호 암호화
        String EncodedPassword = passwordEncoder.encode(userPassword);
        userEntity.setUserPassword(EncodedPassword);

        //UserRepository 를 이용해서 데이터베이스에 Entity 저장
        try {
            userRepository.save(userEntity);
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error!");
        }

        //성공시 success response 반환
        return ResponseDto.setSuccess("Signup Success!", null);
    }


    public ResponseDto<?> signIn(SignInDto dto) {
        String userEmail = dto.getUserEmail();
        String userPassword = dto.getUserPassword();

//        try {
//            boolean existed = userRepository.existsByUserEmailAndUserPassword(userEmail, userPassword);
//            if(!existed) return ResponseDto.setFailed("Sign In Information does not Match");
//        } catch (Exception error){
//            return ResponseDto.setFailed("Data Base Error");
//        }


        UserEntity userEntity = null;
        try {
            //userRepository 에서 userEmail로 검색해서 가져오겠다.
//          userEntity = userRepository.findById(userEmail).get();
          userEntity = userRepository.findByUserEmail(userEmail);
          //잘못된 이메일
          if(userEntity == null)  return ResponseDto.setFailed("Sign In Failed");
          //잘못된 패스워드
          if(!passwordEncoder.matches(userPassword, userEntity.getUserPassword()))
              return ResponseDto.setFailed("Sign In Failed");
        } catch (Exception error){
            return ResponseDto.setFailed("Data Base Error");
        }

        userEntity.setUserPassword("");//가져온 것 중 userPassword 만 초기화

        //토큰생성
        String token = tokenProvider.create(userEmail);
        System.out.println("token : "+ token);
        int exprTime = 3600000;

        SignInResponseDto signInResponseDto = new SignInResponseDto(token, exprTime, userEntity);
        System.out.println(signInResponseDto);
        return ResponseDto.setSuccess("Sign In Success!!", signInResponseDto);

    }


}
