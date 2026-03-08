package kr.ac.dongeui.virtualfitting.domain.user.service;

import kr.ac.dongeui.virtualfitting.domain.user.entity.User;
import kr.ac.dongeui.virtualfitting.domain.user.repository.UserRepository;
import kr.ac.dongeui.virtualfitting.global.security.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String googleLoginOrSignup(String email, String name) {
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            return userRepository.save(newUser);
        });

        return jwtTokenProvider.createToken(user.getEmail());
    }

    public void updateBodyInfo(String email, Double heightCm, String bodyImageUrl) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            return new IllegalArgumentException("가입되지 않은 사용자입니다.");
        });

        user.setHeightCm(heightCm);
        user.setBodyImageUrl(bodyImageUrl);
        userRepository.save(user);
    }
}