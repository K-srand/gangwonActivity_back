package com.multicampus.gangwonActivity.controller;

import com.multicampus.gangwonActivity.dto.request.mycourse.GetMyCourseDto;
import com.multicampus.gangwonActivity.dto.request.mycourse.GetMyFavoritesDto;
import com.multicampus.gangwonActivity.dto.request.mycourse.GetMyFavoritesTotalDto;
import com.multicampus.gangwonActivity.entity.MyFavoritesUserPlace;
import com.multicampus.gangwonActivity.service.implement.CreateMyCourseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/getmycourse")
@RestController
@RequiredArgsConstructor
public class CreateMyCourseController {
    private final CreateMyCourseServiceImpl createMyCourseServiceImpl;

    //전체 플레이스 서비스 호출
    @PostMapping("/getplacetotal")
    public List<MyFavoritesUserPlace> receivePlaceTotal(@AuthenticationPrincipal String userId,
                                                        @RequestBody GetMyFavoritesTotalDto getMyFavoritesTotalDto) {
        getMyFavoritesTotalDto.setUserId(userId);
        return createMyCourseServiceImpl.getPlaceTotal(getMyFavoritesTotalDto);
    }

    //카테고리별 플레이스 서비스 호출
    @PostMapping("/getplacecat")
    public List<MyFavoritesUserPlace> receivePlaceCat(@AuthenticationPrincipal String userId,
                                                      @RequestBody GetMyFavoritesDto getMyFavoritesDto) {
        getMyFavoritesDto.setUserId(userId);
        return createMyCourseServiceImpl.getPlaceCat(getMyFavoritesDto);
    }

    //코스 저장 서비스 호출
    @PostMapping("/getcourse")
    public ResponseEntity<?> saveCourse(@AuthenticationPrincipal String userId,
                                        @RequestBody GetMyCourseDto getMyCourseDto) {
        getMyCourseDto.setUserId(userId);
        return createMyCourseServiceImpl.getMyCourse(getMyCourseDto);
    }
}
