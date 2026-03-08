package kr.ac.dongeui.virtualfitting.domain.fitting.controller;

import kr.ac.dongeui.virtualfitting.domain.fitting.dto.FittingHistoryResponse;
import kr.ac.dongeui.virtualfitting.domain.fitting.repository.FittingHistoryRepository;
import kr.ac.dongeui.virtualfitting.domain.user.entity.User;
import kr.ac.dongeui.virtualfitting.domain.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fittings")
public class FittingController {

    private final FittingHistoryRepository fittingHistoryRepository;
    private final UserRepository userRepository;

    public FittingController(FittingHistoryRepository fittingHistoryRepository, UserRepository userRepository) {
        this.fittingHistoryRepository = fittingHistoryRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/history")
    public List<FittingHistoryResponse> getMyFittingHistory(Authentication authentication) {
        // 출입증에서 이메일 꺼내서 유저 찾기
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 해당 유저의 피팅 이력 조회 후 DTO로 변환해서 반환
        return fittingHistoryRepository.findByUserOrderByIdDesc(user).stream()
                .map(FittingHistoryResponse::new)
                .collect(Collectors.toList());
    }
}