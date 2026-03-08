package kr.ac.dongeui.virtualfitting.domain.clothes.repository;

import kr.ac.dongeui.virtualfitting.domain.clothes.entity.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClothesRepository extends JpaRepository<Clothes, Long> {
}