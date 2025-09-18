package com.example.demo.post.mapper;

import com.example.demo.post.dto.PostWithStatsResponse;
import com.example.demo.auth.dto.MyPageProfileResponse;
import com.example.demo.post.model.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
        int insertPost(Post post); // useGeneratedKeys로 postId 채워짐

        void insertPostTagBatch(@Param("postId") Integer postId,
                        @Param("tags") List<String> tags);

        int increaseViewCount(@Param("postId") Integer postId);

        List<Post> selectPosts(@Param("limit") int limit, @Param("offset") int offset);

        Post selectPostById(@Param("postId") Integer postId);

        List<String> selectTagsByPostId(@Param("postId") Integer postId);

        List<PostWithStatsResponse> selectPostsWithStats(
                        @Param("limit") int limit,
                        @Param("offset") int offset,
                        @Param("viewerId") Integer viewerId);

        PostWithStatsResponse selectPostByIdWithStats(
                        @Param("postId") Integer postId,
                        @Param("viewerId") Integer viewerId);

        MyPageProfileResponse selectMyProfile(@Param("userId") Integer userId);

        // 내가 쓴 글
        List<PostWithStatsResponse> selectPostsByAuthorWithStats(
                        @Param("authorId") Integer authorId,
                        @Param("limit") int limit,
                        @Param("offset") int offset,
                        @Param("viewerId") Integer viewerId);

        // 내가 좋아요한 글
        List<PostWithStatsResponse> selectPostsLikedByUserWithStats(
                        @Param("likerId") Integer likerId,
                        @Param("limit") int limit,
                        @Param("offset") int offset,
                        @Param("viewerId") Integer viewerId);

        // 내가 스크랩한 글
        List<PostWithStatsResponse> selectPostsBookmarkedByUserWithStats(
                        @Param("collectorId") Integer collectorId,
                        @Param("limit") int limit,
                        @Param("offset") int offset,
                        @Param("viewerId") Integer viewerId);

        Integer findAuthorIdByPostId(@Param("postId") Integer postId);

        int deletePost(@Param("postId") Integer postId);
}