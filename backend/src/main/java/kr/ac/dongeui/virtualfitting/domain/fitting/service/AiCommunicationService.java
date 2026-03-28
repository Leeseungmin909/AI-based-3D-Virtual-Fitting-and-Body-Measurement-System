package kr.ac.dongeui.virtualfitting.domain.fitting.service;

import kr.ac.dongeui.virtualfitting.domain.fitting.entity.FittingHistory;
import kr.ac.dongeui.virtualfitting.domain.fitting.entity.FittingStatus;
import kr.ac.dongeui.virtualfitting.domain.fitting.repository.FittingHistoryRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiCommunicationService {

    private final FittingHistoryRepository fittingHistoryRepository;
    private final RestTemplate restTemplate;

    public AiCommunicationService(FittingHistoryRepository fittingHistoryRepository) {
        this.fittingHistoryRepository = fittingHistoryRepository;
        this.restTemplate = new RestTemplate();
    }

    @Async // 이제 외부 클래스에서 호출되므로 완벽하게 비동기로 작동합니다!
    @Transactional
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
            System.err.println("파이썬 서버 통신 실패. 파이썬 서버 상태를 확인하세요. 상태를 FAIL로 변경합니다.");
            // 실패 시 상태를 FAIL로 업데이트
            FittingHistory history = fittingHistoryRepository.findById(fittingId)
                    .orElseThrow(() -> new IllegalArgumentException("피팅 이력을 찾을 수 없습니다."));
            history.setStatus(FittingStatus.FAIL);
        }
    }
}