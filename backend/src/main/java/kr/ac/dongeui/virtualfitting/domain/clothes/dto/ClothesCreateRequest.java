package kr.ac.dongeui.virtualfitting.domain.clothes.dto;

import lombok.Getter;

@Getter
public class ClothesCreateRequest {
    private String name;
    private String category;
    private String imageUrl;
    private String base3dUrl;

    private Double totalLength;
    private Double shoulderWidth;
    private Double chestWidth;
    private Double sleeveLength;

    private Double waistWidth;
    private Double hipWidth;
    private Double thighWidth;
    private Double crotch;
    private Double hemWidth;
}