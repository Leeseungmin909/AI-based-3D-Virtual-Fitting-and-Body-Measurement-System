package kr.ac.dongeui.virtualfitting.domain.file.controller;

import kr.ac.dongeui.virtualfitting.global.infra.s3.S3UploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final S3UploadService s3UploadService;

    public FileUploadController(S3UploadService s3UploadService) {
        this.s3UploadService = s3UploadService;
    }

    // 파일 하나와 폴더 이름을 받아서 클라우드에 올리는 업로드 API
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folder") String folder) {

        try {
            System.out.println("파일 업로드 요청 수신! 대상 폴더: " + folder);

            // S3에 업로드하고 만들어진 URL을 받아옴
            String fileUrl = s3UploadService.uploadFile(file, folder);

            Map<String, String> response = new HashMap<>();
            response.put("message", "S3 파일 업로드 성공!");
            response.put("fileUrl", fileUrl);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", "파일 업로드 중 오류가 발생했습니다."));
        }
    }
}