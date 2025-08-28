package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    
    // 사용자 등록
    int insertUser(User user);
    
    // 사용자명으로 사용자 조회
    User findByUsername(String username);
    
    // 이메일로 사용자 조회
    User findByEmail(String email);
    
    // 사용자 ID로 사용자 조회
    User findByUserId(Integer userId);
    
    // 모든 사용자 조회
    List<User> findAllUsers();
    
    // 사용자 정보 업데이트
    int updateUser(User user);
    
    // 사용자 삭제
    int deleteUser(Integer userId);
    
    // 사용자명 중복 확인
    boolean existsByUsername(String username);
    
    // 이메일 중복 확인
    boolean existsByEmail(String email);

    Integer findIdByUsername(@Param("username") String username);
}
