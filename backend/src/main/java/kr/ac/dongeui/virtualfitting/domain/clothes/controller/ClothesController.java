package kr.ac.dongeui.virtualfitting.domain.clothes.controller;

import kr.ac.dongeui.virtualfitting.domain.clothes.dto.ClothesResponse;
import kr.ac.dongeui.virtualfitting.domain.clothes.repository.ClothesRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clothes")
public class ClothesController {

    private final ClothesRepository clothesRepository;

    public ClothesController(ClothesRepository clothesRepository) {
        this.clothesRepository = clothesRepository;
    }

    @GetMapping
    public List<ClothesResponse> getAllClothes() {
        return clothesRepository.findAll().stream()
                .map(ClothesResponse::new)
                .collect(Collectors.toList());
    }
}