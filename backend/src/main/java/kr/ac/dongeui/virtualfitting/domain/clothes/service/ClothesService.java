package kr.ac.dongeui.virtualfitting.domain.clothes.service;

import kr.ac.dongeui.virtualfitting.domain.clothes.dto.ClothesCreateRequest;
import kr.ac.dongeui.virtualfitting.domain.clothes.entity.Clothes;
import kr.ac.dongeui.virtualfitting.domain.clothes.repository.ClothesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClothesService {

    private final ClothesRepository clothesRepository;

    public ClothesService(ClothesRepository clothesRepository) {
        this.clothesRepository = clothesRepository;
    }

    public Long createClothes(ClothesCreateRequest request) {
        Clothes clothes = new Clothes();
        clothes.setName(request.getName());
        clothes.setCategory(request.getCategory());
        clothes.setImageUrl(request.getImageUrl());
        clothes.setBase3dUrl(request.getBase3dUrl());

        // 사이즈 매핑 (프론트에서 안 보낸 값은 자연스럽게 null로 들어감)
        clothes.setTotalLength(request.getTotalLength());
        clothes.setShoulderWidth(request.getShoulderWidth());
        clothes.setChestWidth(request.getChestWidth());
        clothes.setSleeveLength(request.getSleeveLength());

        clothes.setWaistWidth(request.getWaistWidth());
        clothes.setHipWidth(request.getHipWidth());
        clothes.setThighWidth(request.getThighWidth());
        clothes.setCrotch(request.getCrotch());
        clothes.setHemWidth(request.getHemWidth());

        Clothes savedClothes = clothesRepository.save(clothes);
        return savedClothes.getId();
    }
}