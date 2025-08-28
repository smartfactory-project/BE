# MES Backend System

Manufacturing Execution System - 통합 백엔드 시스템

## 프로젝트 구조

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
      │           │  ├─ QualityChartsController.java
      │           │  └─ ScheduleController.java
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
      │           │  ├─ PostResponse.java
      │           │  ├─ PostWithStatsResponse.java
      │           │  ├─ Production.java
      │           │  ├─ RegisterRequest.java
      │           │  ├─ ScheduleCreateRequest.java
      │           │  ├─ ScheduleResponse.java
      │           │  ├─ TaskCreateRequest.java
      │           │  └─ TaskResponse.java
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
      │           │  ├─ ScheduleMapper.java
      │           │  └─ UserMapper.java
      │           ├─ model
      │           │  ├─ Comment.java
      │           │  ├─ LineMonth.java
      │           │  ├─ Performance.java
      │           │  ├─ Post.java
      │           │  ├─ Production.java
      │           │  ├─ Quality.java
      │           │  ├─ QualityPer.java
      │           │  ├─ Schedule.java
      │           │  ├─ Task.java
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
      │           │  ├─ QualityChartsService.java
      │           │  └─ ScheduleService.java
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
         │  ├─ ScheduleMapper.xml
         │  └─ UserMapper.xml
         └─ schema.sql
```

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