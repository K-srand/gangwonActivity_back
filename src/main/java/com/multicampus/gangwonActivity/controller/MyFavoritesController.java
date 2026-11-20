package com.multicampus.gangwonActivity.controller;

import com.multicampus.gangwonActivity.dto.request.mycourse.GetMyFavoritesMappingDto;
import com.multicampus.gangwonActivity.service.MyFavoriteService;
import com.multicampus.gangwonActivity.service.implement.MyFavoritesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/getmyfavorite")
@RestController
@RequiredArgsConstructor
public class MyFavoritesController {

    private final MyFavoritesServiceImpl myFavoritesServiceImpl;

    //찜 리스트 가져오기 서비스 호출
    @PostMapping("")
    public ResponseEntity<?> getMyFavorite(@AuthenticationPrincipal String userId,
                                           @RequestBody GetMyFavoritesMappingDto getMyFavoritesMappingDto) {
        // 요청 본문의 userId 대신 토큰의 사용자로 고정 (다른 사용자 이름으로 찜하는 것 방지)
        getMyFavoritesMappingDto.setUserId(userId);
        return myFavoritesServiceImpl.getMyFavorite(getMyFavoritesMappingDto);
    }
}
