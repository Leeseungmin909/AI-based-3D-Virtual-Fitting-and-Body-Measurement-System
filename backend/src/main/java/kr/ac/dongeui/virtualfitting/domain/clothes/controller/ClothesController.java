package kr.ac.dongeui.virtualfitting.domain.clothes.controller;

import kr.ac.dongeui.virtualfitting.domain.clothes.dto.ClothesCreateRequest;
import kr.ac.dongeui.virtualfitting.domain.clothes.dto.ClothesResponse;
import kr.ac.dongeui.virtualfitting.domain.clothes.repository.ClothesRepository;
import kr.ac.dongeui.virtualfitting.domain.clothes.service.ClothesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clothes")
public class ClothesController {

    private final ClothesRepository clothesRepository;
    private final ClothesService clothesService;

    public ClothesController(ClothesRepository clothesRepository, ClothesService clothesService) {
        this.clothesRepository = clothesRepository;
        this.clothesService = clothesService;
    }

    // 의류 목록 조회 API
    @GetMapping
    public List<ClothesResponse> getAllClothes() {
        return clothesRepository.findAll().stream()
                .map(ClothesResponse::new)
                .collect(Collectors.toList());
    }

    // 신규 의류 등록 API
    @PostMapping
    public ResponseEntity<Map<String, Object>> createClothes(@RequestBody ClothesCreateRequest request) {
        Long clothesId = clothesService.createClothes(request);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "옷 데이터가 성공적으로 등록되었습니다.");
        response.put("clothesId", clothesId);

        return ResponseEntity.ok(response);
    }
}