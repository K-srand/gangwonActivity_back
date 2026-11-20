package com.multicampus.gangwonActivity.service.implement;

import com.multicampus.gangwonActivity.dto.response.ResponseDto;
import com.multicampus.gangwonActivity.dto.response.user.GetSignInUserResponseDto;
import com.multicampus.gangwonActivity.entity.User;
import com.multicampus.gangwonActivity.repository.UserRepository;
import com.multicampus.gangwonActivity.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    //로그인
    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String id) {

        User user = null;

        try {
            user = userRepository.findByUserId(id);
            if(user ==null) return GetSignInUserResponseDto.notExistUser();
        }catch (Exception e){
            log.error("getSignInUser 처리 중 오류", e);
            return ResponseDto.databaseError();
        }

        return GetSignInUserResponseDto.success(user);
    }
}
