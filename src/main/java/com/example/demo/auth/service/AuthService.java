package com.example.demo.auth.service;

import com.example.demo.auth.dto.AuthResponse;
import com.example.demo.auth.dto.LoginRequest;
import com.example.demo.auth.dto.RegisterRequest;
import com.example.demo.auth.mapper.UserMapper;
import com.example.demo.auth.model.User;
import com.example.demo.auth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    // 회원가입
    public AuthResponse register(RegisterRequest request) {
        // 비밀번호 확인
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 사용자명 중복 확인
        if (userMapper.existsByUsername(request.getUsername())) {
            throw new RuntimeException("이미 사용 중인 사용자명입니다.");
        }

        // 이메일 중복 확인
        if (userMapper.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }

        // 새 사용자 생성
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        user.setIsActive(true);

        userMapper.insertUser(user);

        // JWT 토큰 생성
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getIsActive(),
                true, true, true,
                java.util.Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );

        String token = jwtUtil.generateToken(userDetails);

        return new AuthResponse(token, user.getUserId(), user.getUsername(), user.getEmail(), user.getRole());
    }

    // 로그인
    public AuthResponse login(LoginRequest request) {
        System.out.println("로그인 시도: " + request.getUsername());
        
        // 사용자 조회 테스트
        User user = userMapper.findByUsername(request.getUsername());
        if (user == null) {
            System.out.println("사용자를 찾을 수 없음: " + request.getUsername());
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        System.out.println("사용자 찾음: " + user.getUsername() + ", 비밀번호 길이: " + user.getPassword().length());
        
        // 비밀번호 검증 테스트
        System.out.println("입력된 비밀번호: " + request.getPassword());
        System.out.println("저장된 비밀번호 해시: " + user.getPassword());
        boolean passwordMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());
        System.out.println("비밀번호 일치 여부: " + passwordMatch);
        
        // 인증
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("인증 성공: " + userDetails.getUsername());

            // JWT 토큰 생성
            String token = jwtUtil.generateToken(userDetails);

            return new AuthResponse(token, user.getUserId(), user.getUsername(), user.getEmail(), user.getRole());
        } catch (Exception e) {
            System.out.println("인증 실패: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("자격 증명에 실패하였습니다.");
        }
    }

    // 현재 사용자 정보 조회
    public User getCurrentUser(String token) {
        String actualToken = token.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(actualToken);
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        return user;
    }

    // 회원 정보 수정
    public User updateProfile(String token, Map<String, String> request) {
        User currentUser = getCurrentUser(token);
        
        String newUsername = request.get("username");
        String newEmail = request.get("email");
        
        // 사용자명 중복 확인 (자신 제외)
        if (newUsername != null && !newUsername.equals(currentUser.getUsername())) {
            if (userMapper.existsByUsername(newUsername)) {
                throw new RuntimeException("이미 사용 중인 사용자명입니다.");
            }
            currentUser.setUsername(newUsername);
        }
        
        // 이메일 중복 확인 (자신 제외)
        if (newEmail != null && !newEmail.equals(currentUser.getEmail())) {
            if (userMapper.existsByEmail(newEmail)) {
                throw new RuntimeException("이미 사용 중인 이메일입니다.");
            }
            currentUser.setEmail(newEmail);
        }
        
        userMapper.updateUser(currentUser);
        return currentUser;
    }

    // 비밀번호 변경
    public void changePassword(String token, String currentPassword, String newPassword) {
        User currentUser = getCurrentUser(token);
        
        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }
        
        // 새 비밀번호 유효성 검사
        if (newPassword == null || newPassword.length() < 6) {
            throw new RuntimeException("새 비밀번호는 6자 이상이어야 합니다.");
        }
        
        // 새 비밀번호로 업데이트
        currentUser.setPassword(passwordEncoder.encode(newPassword));
        userMapper.updateUser(currentUser);
    }

    // 회원탈퇴
    public void deleteAccount(String token, String password) {
        User currentUser = getCurrentUser(token);
        
        // 비밀번호 확인
        if (!passwordEncoder.matches(password, currentUser.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        
        userMapper.deleteUser(currentUser.getUserId());
    }
}
