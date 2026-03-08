package kr.ac.dongeui.virtualfitting.domain.clothes.dto;

import kr.ac.dongeui.virtualfitting.domain.clothes.entity.Clothes;
import lombok.Getter;

@Getter
public class ClothesResponse {
    private Long id;
    private String name;
    private String category;
    private String imageUrl;

    public ClothesResponse(Clothes clothes) {
        this.id = clothes.getId();
        this.name = clothes.getName();
        this.category = clothes.getCategory();
        this.imageUrl = clothes.getImageUrl();
    }
}