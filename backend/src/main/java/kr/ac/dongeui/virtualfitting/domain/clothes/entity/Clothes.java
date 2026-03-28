package kr.ac.dongeui.virtualfitting.domain.clothes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "clothes")
public class Clothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String category; // 예: "TOP", "BOTTOM", "OUTER"

    @Column(nullable = false, columnDefinition = "TEXT")
    private String imageUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String base3dUrl;

    // --- 사이즈 정보 (NULL 허용: 카테고리에 따라 안 쓰는 값이 있음) ---
    // 공통
    private Double totalLength;   // 총장

    // 상의 & 아우터 전용
    private Double shoulderWidth; // 어깨너비
    private Double chestWidth;    // 가슴단면
    private Double sleeveLength;  // 소매길이

    // 하의 전용
    private Double waistWidth;    // 허리단면
    private Double hipWidth;      // 엉덩이단면
    private Double thighWidth;    // 허벅지단면
    private Double crotch;        // 밑위
    private Double hemWidth;      // 밑단단면
}