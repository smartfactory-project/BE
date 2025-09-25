
```
BE
├─ .mvn
│  └─ wrapper
│     └─ maven-wrapper.properties
├─ mvnw
├─ mvnw.cmd
├─ pom.xml
└─ src
   └─ main
      ├─ java
      │  └─ com
      │     └─ example
      │        └─ demo
      │           ├─ auth
      │           │  ├─ controller
      │           │  │  ├─ AuthController.java
      │           │  │  └─ MeController.java
      │           │  ├─ dto
      │           │  │  ├─ AuthResponse.java
      │           │  │  ├─ LoginRequest.java
      │           │  │  ├─ MyPageProfileResponse.java
      │           │  │  └─ RegisterRequest.java
      │           │  ├─ mapper
      │           │  │  └─ UserMapper.java
      │           │  ├─ model
      │           │  │  └─ User.java
      │           │  ├─ security
      │           │  │  ├─ JwtAuthenticationFilter.java
      │           │  │  └─ JwtUtil.java
      │           │  └─ service
      │           │     ├─ AuthService.java
      │           │     ├─ CustomUserDetailsService.java
      │           │     └─ MeService.java
      │           ├─ common
      │           │  ├─ config
      │           │  │  └─ SecurityConfig.java
      │           │  └─ exception
      │           │     ├─ ForbiddenException.java
      │           │     └─ NotFoundException.java
      │           ├─ DemoApplication.java
      │           ├─ error
      │           │  ├─ controller
      │           │  │  └─ ErrorCodeController.java
      │           │  ├─ dto
      │           │  │  ├─ ErrorCodeCreateRequest.java
      │           │  │  ├─ ErrorCodeResponse.java
      │           │  │  └─ ErrorCodeUpdateRequest.java
      │           │  ├─ mapper
      │           │  │  └─ ErrorCodeMapper.java
      │           │  ├─ model
      │           │  │  └─ ErrorCode.java
      │           │  └─ service
      │           │     └─ ErrorCodeService.java
      │           ├─ post
      │           │  ├─ controller
      │           │  │  ├─ CommentController.java
      │           │  │  ├─ PostBookmarkController.java
      │           │  │  ├─ PostController.java
      │           │  │  └─ PostLikeController.java
      │           │  ├─ dto
      │           │  │  ├─ BookmarkStatusResponse.java
      │           │  │  ├─ CommentCreateRequest.java
      │           │  │  ├─ CommentResponse.java
      │           │  │  ├─ LikeStatusResponse.java
      │           │  │  ├─ PostCreateRequest.java
      │           │  │  ├─ PostResponse.java
      │           │  │  └─ PostWithStatsResponse.java
      │           │  ├─ mapper
      │           │  │  ├─ CommentMapper.java
      │           │  │  ├─ PostBookmarkMapper.java
      │           │  │  ├─ PostLikeMapper.java
      │           │  │  └─ PostMapper.java
      │           │  ├─ model
      │           │  │  ├─ Comment.java
      │           │  │  └─ Post.java
      │           │  └─ service
      │           │     ├─ CommentService.java
      │           │     ├─ PostBookmarkService.java
      │           │     ├─ PostLikeService.java
      │           │     └─ PostService.java
      │           ├─ quality
      │           │  ├─ controller
      │           │  │  ├─ ProductionController.java
      │           │  │  └─ QualityChartsController.java
      │           │  ├─ dto
      │           │  │  └─ Production.java
      │           │  ├─ mapper
      │           │  │  ├─ ProductionMapper.java
      │           │  │  └─ QualityChartsMapper.java
      │           │  ├─ model
      │           │  │  ├─ LineMonth.java
      │           │  │  ├─ Performance.java
      │           │  │  ├─ Production.java
      │           │  │  ├─ Quality.java
      │           │  │  └─ QualityPer.java
      │           │  └─ service
      │           │     ├─ ProductionService.java
      │           │     └─ QualityChartsService.java
      │           ├─ routing
      │           │  ├─ controller
      │           │  │  ├─ ProcessCarController.java
      │           │  │  ├─ ProcessRoutingController.java
      │           │  │  └─ ProcessStationController.java
      │           │  ├─ dto
      │           │  │  ├─ CarNameResponse.java
      │           │  │  ├─ FactoryResponse.java
      │           │  │  ├─ ProductRoutingGetResponse.java
      │           │  │  ├─ ProductRoutingNodeResponse.java
      │           │  │  └─ StationResponse.java
      │           │  ├─ mapper
      │           │  │  ├─ CarNameMapper.java
      │           │  │  ├─ FactoryMapper.java
      │           │  │  ├─ ProductRoutingMapper.java
      │           │  │  └─ StationMapper.java
      │           │  ├─ model
      │           │  │  ├─ CarName.java
      │           │  │  ├─ ProductRouting.java
      │           │  │  └─ Station.java
      │           │  └─ service
      │           │     ├─ CarNameService.java
      │           │     ├─ FactoryService.java
      │           │     ├─ ProductRoutingService.java
      │           │     └─ StationService.java
      │           └─ schedule
      │              ├─ controller
      │              │  └─ ScheduleController.java
      │              ├─ dto
      │              │  ├─ ScheduleCreateRequest.java
      │              │  ├─ ScheduleResponse.java
      │              │  ├─ TaskCreateRequest.java
      │              │  └─ TaskResponse.java
      │              ├─ mapper
      │              │  └─ ScheduleMapper.java
      │              ├─ model
      │              │  ├─ Schedule.java
      │              │  └─ Task.java
      │              └─ service
      │                 └─ ScheduleService.java
      └─ resources
         ├─ application.properties
         ├─ data.sql
         ├─ data2.sql
         ├─ mapper
         │  ├─ auth
         │  │  └─ UserMapper.xml
         │  ├─ post
         │  │  ├─ CommentMapper.xml
         │  │  ├─ PostBookmarkMapper.xml
         │  │  ├─ PostLikeMapper.xml
         │  │  └─ PostMapper.xml
         │  ├─ quality
         │  │  ├─ ProductionMapper.xml
         │  │  └─ QualityChartsMapper.xml
         │  ├─ routing
         │  │  ├─ CarNameMapper.xml
         │  │  ├─ FactoryMapper.xml
         │  │  ├─ ProductRoutingMapper.xml
         │  │  └─ StationMapper.xml
         │  └─ schedule
         │     └─ ScheduleMapper.xml
         ├─ schema1.sql
         └─ schema2.sql

```