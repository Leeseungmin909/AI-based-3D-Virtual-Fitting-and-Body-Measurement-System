package kr.ac.dongeui.virtualfitting.domain.fitting.dto;

import kr.ac.dongeui.virtualfitting.domain.fitting.entity.FittingHistory;
import kr.ac.dongeui.virtualfitting.domain.fitting.entity.FittingStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FittingHistoryResponse {
    private Long historyId;
    private String clothesName;
    private String resultSplatUrl;
    private FittingStatus status;
    private LocalDateTime createdAt;

    public FittingHistoryResponse(FittingHistory history) {
        this.historyId = history.getId();
        this.clothesName = history.getClothes().getName();
        this.resultSplatUrl = history.getResultSplatUrl();
        this.status = history.getStatus();
        this.createdAt = history.getCreatedAt();
    }
}