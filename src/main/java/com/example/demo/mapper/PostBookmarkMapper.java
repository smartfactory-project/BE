package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostBookmarkMapper {
    int insertBookmark(@Param("postId") Integer postId, @Param("userId") Integer userId);
    int deleteBookmark(@Param("postId") Integer postId, @Param("userId") Integer userId);
    boolean existsByPostIdAndUserId(@Param("postId") Integer postId, @Param("userId") Integer userId);
    int countByPostId(@Param("postId") Integer postId);
}
