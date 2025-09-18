## 기능

### 인증 시스템
- JWT 기반 로그인/회원가입
- 보안 설정 및 권한 관리

### 커뮤니티 기능
- 게시글 CRUD
- 댓글 시스템
- 좋아요/북마크 기능

### 일정 관리
- 스케줄 관리
- 작업(Task) 관리

### 생산 관리 및 분석
- 생산 데이터 관리
- 품질 차트 및 분석
- 성능 지표 모니터링

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
         └─ schema.sql

```