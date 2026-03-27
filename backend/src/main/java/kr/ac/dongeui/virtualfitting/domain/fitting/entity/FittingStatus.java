package kr.ac.dongeui.virtualfitting.domain.fitting.entity;

public enum FittingStatus {
    PENDING, // 스프링 부트가 요청을 접수하고 AI 서버의 처리를 기다리는 상태
    PROCESSING, // 파이썬 AI 서버가 렌더링 중
    SUCCESS,    // 렌더링 성공 및 스플랫 파일 URL 발급 완료
    FAIL        // 렌더링 실패
}