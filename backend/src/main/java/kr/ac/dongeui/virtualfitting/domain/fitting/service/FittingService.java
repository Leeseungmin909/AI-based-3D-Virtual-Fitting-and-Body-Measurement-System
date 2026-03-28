package kr.ac.dongeui.virtualfitting.domain.fitting.service;

import kr.ac.dongeui.virtualfitting.domain.clothes.entity.Clothes;
import kr.ac.dongeui.virtualfitting.domain.clothes.repository.ClothesRepository;
import kr.ac.dongeui.virtualfitting.domain.fitting.entity.FittingHistory;
import kr.ac.dongeui.virtualfitting.domain.fitting.entity.FittingStatus;
import kr.ac.dongeui.virtualfitting.domain.fitting.repository.FittingHistoryRepository;
import kr.ac.dongeui.virtualfitting.domain.user.entity.User;
import kr.ac.dongeui.virtualfitting.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FittingService {

    private final FittingHistoryRepository fittingHistoryRepository;
    private final UserRepository userRepository;
    private final ClothesRepository clothesRepository;
    private final AiCommunicationService aiCommunicationService;

    public FittingService(FittingHistoryRepository fittingHistoryRepository,
                          UserRepository userRepository,
                          ClothesRepository clothesRepository,
                          AiCommunicationService aiCommunicationService) {
        this.fittingHistoryRepository = fittingHistoryRepository;
        this.userRepository = userRepository;
        this.clothesRepository = clothesRepository;
        this.aiCommunicationService = aiCommunicationService;
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

        fittingHistoryRepository.save(history);

        aiCommunicationService.sendToPythonServer(
                history.getId(),
                user.getSmplMannequinUrl(),
                clothes.getBase3dUrl()
        );

        return history.getId();
    }

    @Transactional
    public void completeFitting(Long fittingId, String resultSplatUrl) {
        FittingHistory history = fittingHistoryRepository.findById(fittingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 피팅 이력을 찾을 수 없습니다. ID: " + fittingId));

        history.setStatus(FittingStatus.SUCCESS);
        history.setResultSplatUrl(resultSplatUrl);

        System.out.println("피팅 완료 처리 성공! S3 결과물 URL: " + resultSplatUrl);
    }
}