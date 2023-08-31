package com.example.board2.service;

import com.example.board2.dto.PatchUserDto;
import com.example.board2.dto.PatchUserResponseDto;
import com.example.board2.dto.ResponseDto;
import com.example.board2.entity.UserEntity;
import com.example.board2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class USerService {
    @Autowired
    UserRepository userRepository;

    public ResponseDto<PatchUserResponseDto> patchUser(PatchUserDto requestBody, String userEmail) {
        UserEntity userEntity = null;
        String userNickName = requestBody.getUserNickName();
        String userProfile = requestBody.getUserProfile();

        try {
            userEntity = userRepository.findByUserEmail(userEmail);
            if(userEntity == null) return ResponseDto.setFailed("Does Not Exist User");

            userEntity.setUserNickname(userNickName);
            userEntity.setUserProfile(userProfile);

            userRepository.save(userEntity);
        } catch (Exception exception){
            exception.printStackTrace();
            ResponseDto.setFailed("Database Error");
        }

        userEntity.setUserPassword("");

        PatchUserResponseDto patchUserResponseDto = new PatchUserResponseDto(userEntity);

        return ResponseDto.setSuccess("Success", patchUserResponseDto);
    }
}
