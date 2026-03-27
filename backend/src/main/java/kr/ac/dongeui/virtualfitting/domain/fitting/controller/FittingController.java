package kr.ac.dongeui.virtualfitting.domain.fitting.controller;

import kr.ac.dongeui.virtualfitting.domain.fitting.dto.FittingHistoryResponse;
import kr.ac.dongeui.virtualfitting.domain.fitting.repository.FittingHistoryRepository;
import kr.ac.dongeui.virtualfitting.domain.fitting.service.FittingService;
import kr.ac.dongeui.virtualfitting.domain.user.entity.User;
import kr.ac.dongeui.virtualfitting.domain.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fittings")
public class FittingController {

    private final FittingHistoryRepository fittingHistoryRepository;
    private final UserRepository userRepository;
    private final FittingService fittingService;

    public FittingController(FittingHistoryRepository fittingHistoryRepository,
                             UserRepository userRepository,
                             FittingService fittingService) {
        this.fittingHistoryRepository = fittingHistoryRepository;
        this.userRepository = userRepository;
        this.fittingService = fittingService;
    }

    @GetMapping("/history")
    public List<FittingHistoryResponse> getMyFittingHistory(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return fittingHistoryRepository.findByUserOrderByIdDesc(user).stream()
                .map(FittingHistoryResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/request")
    public ResponseEntity<Map<String, Object>> requestVirtualFitting(
            Authentication authentication,
            @RequestBody Map<String, Long> requestData) {

        String userEmail = authentication.getName();
        Long clothesId = requestData.get("clothesId");

        System.out.println("피팅 요청 수신. 유저: " + userEmail + ", 옷 번호: " + clothesId);

        Long fittingId = fittingService.requestFitting(userEmail, clothesId);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "PENDING");
        response.put("fittingId", fittingId);
        response.put("message", "파이썬 서버로 가우시안 스플래팅 렌더링을 요청했습니다. 잠시만 기다려주세요.");

        return ResponseEntity.ok(response);
    }
}