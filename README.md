# BE_real
```
BE
├─ .mvn
│  └─ wrapper
│     └─ maven-wrapper.properties
├─ mvnw
├─ mvnw.cmd
├─ pom.xml
├─ README.md
└─ src
   └─ main
      ├─ java
      │  └─ com
      │     └─ example
      │        └─ demo
      │           ├─ config
      │           │  └─ SecurityConfig.java
      │           ├─ controller
      │           │  ├─ AuthController.java
      │           │  ├─ CommentController.java
      │           │  ├─ MeController.java
      │           │  ├─ PostBookmarkController.java
      │           │  ├─ PostController.java
      │           │  ├─ PostLikeController.java
      │           │  ├─ ProductionController.java
      │           │  └─ QualityChartsController.java
      │           ├─ DemoApplication.java
      │           ├─ dto
      │           │  ├─ AuthResponse.java
      │           │  ├─ BookmarkStatusResponse.java
      │           │  ├─ CommentCreateRequest.java
      │           │  ├─ CommentResponse.java
      │           │  ├─ LikeStatusResponse.java
      │           │  ├─ LoginRequest.java
      │           │  ├─ MyPageProfileResponse.java
      │           │  ├─ PostCreateRequest.java
      │           │  ├─ PostWithStatsResponse.java
      │           │  ├─ Production.java
      │           │  └─ RegisterRequest.java
      │           ├─ exception
      │           │  ├─ ForbiddenException.java
      │           │  └─ NotFoundException.java
      │           ├─ mapper
      │           │  ├─ CommentMapper.java
      │           │  ├─ PostBookmarkMapper.java
      │           │  ├─ PostLikeMapper.java
      │           │  ├─ PostMapper.java
      │           │  ├─ ProductionMapper.java
      │           │  ├─ QualityChartsMapper.java
      │           │  └─ UserMapper.java
      │           ├─ model
      │           │  ├─ Comment.java
      │           │  ├─ LineMonth.java
      │           │  ├─ Performance.java
      │           │  ├─ Post.java
      │           │  ├─ Production.java
      │           │  ├─ Quality.java
      │           │  ├─ QualityPer.java
      │           │  └─ User.java
      │           ├─ security
      │           │  └─ JwtAuthenticationFilter.java
      │           ├─ service
      │           │  ├─ AuthService.java
      │           │  ├─ CommentService.java
      │           │  ├─ CustomUserDetailsService.java
      │           │  ├─ MeService.java
      │           │  ├─ PostBookmarkService.java
      │           │  ├─ PostLikeService.java
      │           │  ├─ PostService.java
      │           │  ├─ ProductionService.java
      │           │  └─ QualityChartsService.java
      │           └─ util
      │              └─ JwtUtil.java
      └─ resources
         ├─ application.properties
         ├─ mapper
         │  ├─ CommentMapper.xml
         │  ├─ PostBookmarkMapper.xml
         │  ├─ PostLikeMapper.xml
         │  ├─ PostMapper.xml
         │  ├─ ProductonMapper.xml
         │  ├─ QualityChartsMapper.xml
         │  └─ UserMapper.xml
         └─ schema.sql

```