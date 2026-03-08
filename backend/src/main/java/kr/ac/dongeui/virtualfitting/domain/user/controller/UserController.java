package kr.ac.dongeui.virtualfitting.domain.user.controller;

import kr.ac.dongeui.virtualfitting.domain.user.dto.UserBodyInfoRequest;
import kr.ac.dongeui.virtualfitting.domain.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/me/body-info")
    public String updateMyBodyInfo(Authentication authentication, @RequestBody UserBodyInfoRequest request) {
        String email = authentication.getName();
        userService.updateBodyInfo(email, request.getHeightCm(), request.getBodyImageUrl());
        return "체형 정보가 성공적으로 등록되었습니다.";
    }
}