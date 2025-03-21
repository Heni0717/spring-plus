# SPRING PLUS


### 🌱 lv1
#### (1) Transactional의 이해

- `TodoService`가 전체적으로 (readOnly = True)로 되어있음
- saveTodo 메소드에 @Transactional 추가

#### (2) JWT의 이해

- `User` Entity에 `nickname` 필드 추가
- 회원가입, 토큰생성, 토큰에서 정보 추출할 때 nickname도 user정보로써 사용되도록 수정

#### (3) JPA의 이해

- Todo 검색조건 추가(날씨, 기간)
- LocalDate 형식으로 입력받아 LocalDateTime으로 변환
- JPQL로 구현
  - 조건은 있을수도, 없을수도 있음(없으면 모두 조회)
  - 수정일 기준 정렬

#### (4) 컨트롤러 테스트의 이해

- 현재 발생하고 있는 코드는 400
- 기대하는 HttpStatus.Ok는 200코드를 반환함
- 정상적으로 400 BAD_REQUEST를 반환하도록 수정

#### (5) AOP의 이해

- 실행 전 동작할 수 있도록 메소드 실행 시점 변경 `@After` ➡ `@Before`
- 타겟 변경 `UserController` ➡ `UserAdminController.changeUserRole`

---
### 🌿 lv2
#### (6) JPA Cascade

- `Todo`와 `Manager`의 연관관계 속성 추가
- `cascade = CascadeType.ALL, orphanRemoval = true`

#### (7) N+1

- fetch join 사용

#### (8) QueryDSL

- 프레임워크 사용을 위한 설정
  - build.gradle에 의존성 추가
  - EntityManager를 주입받은 JPAQueryFactory를 빈으로 등록
- repository 생성후 적용

#### (9) Spring Security

- JTW 유지(토큰 기반 방식 유지)
    - `JwtAuthenticationFilter` : JWT 인증 필터
    - `JwtAuthenticationToken` : 커스텀 인증 토큰
    - jwt를 검증하고, 이를 SecurityContext에 저장
- Spring Security 적용
    - 관련 의존성 추가
    - PasswordEncoder 삭제(security에서 제공)
    - 기존의 filter ➡ jwt 인증 필터
    - 기존 `ArgumentResolver`, `Auth` 삭제 ➡ `@AuthenticationPrincipal` 어노테이션 사용(security에서 제공)

---
### 🪴 lv3
#### (10) QueryDSL - 검색 기능 만들기

- Projection 사용
- 검색 기능에 사용할 dto추가
- 검색 조건
  1) 부분 제목 검색
  2) 생성일 범위로 검색(기간)
  3) 부분 닉네임 검색
- 반환 : 제목, 담당자수, 댓글수

#### (11) Transactional 심화

- 매니저 등록 요청을 기록하는 로그 테이블 생성
- 실제 매니저 등록 로직에 로그서비스의 메소드 추가


---