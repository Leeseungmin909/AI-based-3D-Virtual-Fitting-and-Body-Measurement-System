package kr.ac.dongeui.virtualfitting.global.infra.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3UploadService {

    private final S3Client s3Client;

    // application.properties에 적어둔 버킷 이름을 가져옵니다.
    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public S3UploadService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    /**
     * @param file 업로드할 파일 (사진 또는 glb 파일)
     * @param folderName 저장할 폴더 이름 (예: "clothes/2d-images", "users/smpl-models")
     * @return S3에 저장된 파일의 최종 접속 URL
     */
    public String uploadFile(MultipartFile file, String folderName) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }

        // 1. 파일 이름 중복 방지를 위한 UUID 생성 (예: 123e4567-e89b..._tshirt.png)
        String originalFilename = file.getOriginalFilename();
        String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

        // 2. S3 내부의 정확한 경로 설정 (폴더명/파일명)
        String s3Key = folderName + "/" + uniqueFilename;

        // 3. AWS S3로 보낼 택배 상자(Request) 포장
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(s3Key)
                .contentType(file.getContentType())
                .build();

        // 4. S3로 파일 전송 (업로드 실행)
        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        // 5. 방금 업로드한 파일의 인터넷 접속 URL 주소 조립해서 반환
        return "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + s3Key;
    }
}