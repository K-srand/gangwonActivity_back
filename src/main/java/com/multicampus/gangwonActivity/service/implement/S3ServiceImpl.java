package com.multicampus.gangwonActivity.service.implement;

import com.multicampus.gangwonActivity.entity.Board;
import com.multicampus.gangwonActivity.entity.BoardImage;
import com.multicampus.gangwonActivity.mapper.BoardMapper;
import com.multicampus.gangwonActivity.repository.BoardImageRepository;
import com.multicampus.gangwonActivity.repository.BoardRepository;
import com.multicampus.gangwonActivity.repository.UserRepository;
import com.multicampus.gangwonActivity.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3ServiceImpl implements S3Service {


    private final S3Client s3Client;
    private final BoardMapper boardMapper;
    private final BoardImageRepository boardImageRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 허용 확장자와 S3에 저장할 Content-Type (클라이언트가 보낸 값을 그대로 쓰지 않음)
    private static final Map<String, String> ALLOWED_IMAGE_TYPES = Map.of(
            "jpg", "image/jpeg",
            "jpeg", "image/jpeg",
            "png", "image/png",
            "gif", "image/gif",
            "webp", "image/webp"
    );
    private static final long MAX_IMAGE_SIZE = 20L * 1024 * 1024; // 20MB

    @Value("${cloud.aws.s3.bucketName}")
    private String bucket;

    //S3에 이미지 업로드
    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {

        String fileName = multipartFile.getOriginalFilename();
        if (multipartFile.isEmpty() || fileName == null || !fileName.contains(".")) {
            throw new IllegalArgumentException("이미지 파일을 선택해 주세요.");
        }
        if (multipartFile.getSize() > MAX_IMAGE_SIZE) {
            throw new IllegalArgumentException("이미지는 20MB 이하만 업로드할 수 있습니다.");
        }

        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase(Locale.ROOT);
        String contentType = ALLOWED_IMAGE_TYPES.get(extension);
        String clientType = multipartFile.getContentType();
        if (contentType == null || clientType == null || !clientType.toLowerCase(Locale.ROOT).startsWith("image/")) {
            throw new IllegalArgumentException("jpg, jpeg, png, gif, webp 이미지만 업로드할 수 있습니다.");
        }

        String newFileName = UUID.randomUUID() + "." + extension;
        String fileUrl = "https://" + bucket + ".s3.amazonaws.com/" + newFileName;
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(newFileName)
                .contentType(contentType)
                .contentLength(multipartFile.getSize())
                .build();

        s3Client.putObject(putObjectRequest,
                RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));


        return fileUrl;
    }

    //S3 이미지 삭제
    @Override
    public void deleteFile(String fileUrl, String userId) throws IOException {
        // 본인 게시글에 등록된 이미지만 삭제 가능
        // (게시글에 연결되지 않은 URL은 누구 것인지 알 수 없으므로 거부)
        if (fileUrl == null) return;
        List<BoardImage> images = boardImageRepository.findByImageAddress(fileUrl);
        Long userNo = userRepository.findUserNoByUserId(userId);
        boolean isOwner = !images.isEmpty() && userNo != null && images.stream().allMatch(image -> {
            Board board = boardRepository.findByBoardNo(image.getBoardNo());
            return board != null && Objects.equals(board.getUserNo(), userNo);
        });
        if (!isOwner) throw new AccessDeniedException("본인 게시글의 이미지만 삭제할 수 있습니다.");

        try{
            int index = fileUrl.lastIndexOf("com/");
            String newFileName = fileUrl.substring(index + 4);
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(newFileName)
                    .build());
            boardMapper.deleteImageFile(fileUrl);
        } catch (SdkException e){
            log.error("S3 파일 삭제 실패: {}", fileUrl, e);
            throw new IOException("S3 File Delete Error");
        }
    }
}
