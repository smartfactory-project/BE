package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostLikeMapper {
    int insertLike(@Param("postId") Integer postId, @Param("userId") Integer userId);
    int deleteLike(@Param("postId") Integer postId, @Param("userId") Integer userId);
    int countByPostId(@Param("postId") Integer postId);
    boolean existsByPostIdAndUserId(@Param("postId") Integer postId, @Param("userId") Integer userId);
}
