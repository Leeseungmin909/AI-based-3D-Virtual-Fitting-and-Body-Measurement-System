package kr.ac.dongeui.virtualfitting.domain.fitting.repository;

import kr.ac.dongeui.virtualfitting.domain.fitting.entity.FittingHistory;
import kr.ac.dongeui.virtualfitting.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FittingHistoryRepository extends JpaRepository<FittingHistory, Long> {
    // 특정 유저의 피팅 이력을 최신순(id 역순)으로 정렬해서 가져오기
    List<FittingHistory> findByUserOrderByIdDesc(User user);
}