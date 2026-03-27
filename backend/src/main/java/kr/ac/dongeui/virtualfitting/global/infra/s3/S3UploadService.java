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

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.cloud.aws.region.static}")
    private String region;

    public S3UploadService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file, String folderName) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }

        String originalFilename = file.getOriginalFilename();
        String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

        String s3Key = folderName + "/" + uniqueFilename;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(s3Key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + s3Key;
    }
}