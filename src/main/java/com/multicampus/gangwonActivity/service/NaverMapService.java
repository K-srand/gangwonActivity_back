package com.multicampus.gangwonActivity.service;

import com.multicampus.gangwonActivity.dto.request.api.GetNaverMapDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class NaverMapService {

    private final String apiUrl = "https://oapi.map.naver.com/openapi/v3/maps.js";
    private final String directionsApiUrl = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving";

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    //네이버 맵 API 호출
    public String getNaverMapScript() {
        String fullUrl = apiUrl + "?ncpClientId=" + clientId + "&submodules=geocoder";
        return fullUrl;
    }

    //소요시간 호출
    public String getDrivingDuration(GetNaverMapDto getNaverMapDto) {
        String url = directionsApiUrl + "?start=" + getNaverMapDto.getStartLng() + "," + getNaverMapDto.getStartLat() + "&goal="
                + getNaverMapDto.getEndLng() + "," + getNaverMapDto.getEndLat();

        String body = RestClient.create()
                .get()
                .uri(url)
                .header("X-NCP-APIGW-API-KEY-ID", clientId)
                .header("X-NCP-APIGW-API-KEY", clientSecret)
                .retrieve()
                .body(String.class);
        System.out.println(body);
        return body;  // You'll need to parse the response to get the time
    }
}
