package kr.ac.dongeui.virtualfitting.domain.fitting.dto;

import kr.ac.dongeui.virtualfitting.domain.fitting.entity.FittingHistory;
import lombok.Getter;

@Getter
public class FittingHistoryResponse {
    private Long historyId;
    private String clothesName;
    private String result3dUrl;

    public FittingHistoryResponse(FittingHistory history) {
        this.historyId = history.getId();
        this.clothesName = history.getClothes().getName();
        this.result3dUrl = history.getResult3dUrl();
    }
}