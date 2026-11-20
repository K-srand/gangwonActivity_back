package com.multicampus.gangwonActivity.service.implement;

import com.multicampus.gangwonActivity.mapper.BoardMapper;
import com.multicampus.gangwonActivity.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {


    private final S3Client s3Client;
    private final BoardMapper boardMapper;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucket;

    //S3에 이미지 업로드
    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {

        String fileName = multipartFile.getOriginalFilename();

        String newFileName = UUID.randomUUID()+fileName.substring(fileName.lastIndexOf("."));
        String fileUrl = "https://" + bucket + ".s3.amazonaws.com/" + newFileName;
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(newFileName)
                .contentType(multipartFile.getContentType())
                .contentLength(multipartFile.getSize())
                .build();

        s3Client.putObject(putObjectRequest,
                RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));


        return fileUrl;
    }

    //S3 이미지 삭제
    @Override
    public void deleteFile(String fileUrl) throws IOException {
        try{
            if(fileUrl != null) {
                int index = fileUrl.lastIndexOf("com/");
                String newFileName = fileUrl.substring(index + 4);
                s3Client.deleteObject(DeleteObjectRequest.builder()
                        .bucket(bucket)
                        .key(newFileName)
                        .build());
                boardMapper.deleteImageFile(fileUrl);
            }
        } catch (SdkException e){
            System.out.println("error message" + e.getMessage());
            throw new IOException("S3 File Delete Error");
        }
    }
}
