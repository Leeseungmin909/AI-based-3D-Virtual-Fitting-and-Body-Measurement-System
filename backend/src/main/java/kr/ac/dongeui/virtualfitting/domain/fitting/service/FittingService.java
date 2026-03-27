package kr.ac.dongeui.virtualfitting.domain.fitting.service;

import kr.ac.dongeui.virtualfitting.domain.clothes.entity.Clothes;
import kr.ac.dongeui.virtualfitting.domain.clothes.repository.ClothesRepository;
import kr.ac.dongeui.virtualfitting.domain.fitting.entity.FittingHistory;
import kr.ac.dongeui.virtualfitting.domain.fitting.entity.FittingStatus;
import kr.ac.dongeui.virtualfitting.domain.fitting.repository.FittingHistoryRepository;
import kr.ac.dongeui.virtualfitting.domain.user.entity.User;
import kr.ac.dongeui.virtualfitting.domain.user.repository.UserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class FittingService {

    private final FittingHistoryRepository fittingHistoryRepository;
    private final UserRepository userRepository;
    private final ClothesRepository clothesRepository;
    private final RestTemplate restTemplate;

    public FittingService(FittingHistoryRepository fittingHistoryRepository,
                          UserRepository userRepository,
                          ClothesRepository clothesRepository) {
        this.fittingHistoryRepository = fittingHistoryRepository;
        this.userRepository = userRepository;
        this.clothesRepository = clothesRepository;
        this.restTemplate = new RestTemplate();
    }

    public Long requestFitting(String email, Long clothesId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        Clothes clothes = clothesRepository.findById(clothesId)
                .orElseThrow(() -> new IllegalArgumentException("해당 옷을 찾을 수 없습니다."));

        FittingHistory history = new FittingHistory();
        history.setUser(user);
        history.setClothes(clothes);
        history.setStatus(FittingStatus.PENDING);
        history.setCreatedAt(LocalDateTime.now());

        fittingHistoryRepository.save(history);

        sendToPythonServer(
                history.getId(),
                user.getSmplMannequinUrl(),
                clothes.getBase3dUrl()
        );

        return history.getId();
    }

    @Async
    public void sendToPythonServer(Long fittingId, String smplMannequinUrl, String base3dUrl) {
        try {
            System.out.println("파이썬 AI 서버로 3D 피팅 연산 요청 전송 완료. 피팅 ID: " + fittingId);

            String pythonAiServerUrl = "http://localhost:8000/api/ai/fit";

            Map<String, Object> requestData = new HashMap<>();
            requestData.put("fitting_id", fittingId);
            requestData.put("smpl_mannequin_url", smplMannequinUrl);
            requestData.put("base_3d_url", base3dUrl);

            restTemplate.postForEntity(pythonAiServerUrl, requestData, String.class);
            System.out.println("파이썬 서버 통신 완료. 백그라운드 스레드 종료.");

        } catch (Exception e) {
            System.err.println("파이썬 서버 통신 실패. 파이썬 서버 상태를 확인하세요.");
        }
    }
}