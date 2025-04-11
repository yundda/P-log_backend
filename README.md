# P-log_backend 컨벤션

| 태그 이름            | 설명                                                               |
| -------------------- | ------------------------------------------------------------------ |
| **Feat**             | 새로운 기능을 추가할 경우                                          |
| **Fix**              | 버그를 고친 경우                                                   |
| **Design**           | CSS 등 사용자 UI 디자인 변경                                       |
| **!BREAKING CHANGE** | 커다란 API 변경의 경우                                             |
| **!HOTFIX**          | 급하게 치명적인 버그를 고쳐야 하는 경우                            |
| **Style**            | 코드 포맷 변경, 세미콜론 누락 등 (코드 수정 없음)                  |
| **Refactor**         | 프로덕션 코드 리팩토링                                             |
| **Comment**          | 필요한 주석 추가 및 변경                                           |
| **Docs**             | 문서를 수정한 경우                                                 |
| **Test**             | 테스트 추가, 테스트 리팩토링 (프로덕션 코드 변경 없음)             |
| **Chore**            | 빌드 태스크 업데이트, 패키지 매니저 설정 (프로덕션 코드 변경 없음) |
| **Rename**           | 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우                 |
| **Remove**           | 파일을 삭제하는 작업만 수행한 경우                                 |

```
P-log_backend
├─ .DS_Store
├─ .mvn
│  └─ wrapper
│     └─ maven-wrapper.properties
├─ README.md
├─ mvnw
├─ mvnw.cmd
├─ pom.xml
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ com
   │  │     └─ example
   │  │        └─ plog
   │  │           ├─ PlogApplication.java
   │  │           ├─ config
   │  │           │  ├─ DotenvConfig.java
   │  │           │  ├─ JdbcConfig.java
   │  │           │  ├─ JpaConfig.java
   │  │           │  ├─ WebSecurityConfig.java
   │  │           │  ├─ jwt
   │  │           │  │  └─ JwtProperties.java
   │  │           │  └─ security
   │  │           │     └─ SecurityConfig.java
   │  │           ├─ repository
   │  │           │  ├─ BaseEntity.java
   │  │           │  ├─ Enum
   │  │           │  │  ├─ Gender.java
   │  │           │  │  ├─ Mealtype.java
   │  │           │  │  ├─ Role.java
   │  │           │  │  ├─ Status.java
   │  │           │  │  └─ Type.java
   │  │           │  ├─ detaillog
   │  │           │  │  ├─ DetaillogEntity.java
   │  │           │  │  └─ DetaillogJpaRepository.java
   │  │           │  ├─ family
   │  │           │  │  ├─ FamilyEntity.java
   │  │           │  │  └─ FamilyJpaRepository.java
   │  │           │  ├─ healthlog
   │  │           │  │  ├─ HealthlogEntity.java
   │  │           │  │  └─ HealthlogJpaRepository.java
   │  │           │  ├─ pet
   │  │           │  │  ├─ PetEntity.java
   │  │           │  │  └─ PetJpaRepository.java
   │  │           │  ├─ petlog
   │  │           │  │  ├─ PetlogEntity.java
   │  │           │  │  └─ PetlogJpaRepository.java
   │  │           │  ├─ request
   │  │           │  │  ├─ RequestEntity.java
   │  │           │  │  └─ RequestJpaRepository.java
   │  │           │  └─ user
   │  │           │     ├─ UserEntity.java
   │  │           │     └─ UserJpaRepository.java
   │  │           ├─ security
   │  │           │  ├─ JwtAuthenticationFilter.java
   │  │           │  └─ TokenProvider.java
   │  │           ├─ service
   │  │           │  ├─ AuthService.java
   │  │           │  ├─ PetProfileService.java
   │  │           │  ├─ UserService.java
   │  │           │  ├─ exceptions
   │  │           │  │  ├─ AuthorizationException.java
   │  │           │  │  ├─ DatabaseException.java
   │  │           │  │  ├─ InvalidValueException.java
   │  │           │  │  └─ NotFoundException.java
   │  │           │  └─ mapper
   │  │           │     ├─ PetProfileMapper.java
   │  │           │     └─ UserMapper.java
   │  │           └─ web
   │  │              ├─ advice
   │  │              │  └─ ExceptionalControllerAdvice.java
   │  │              ├─ controller
   │  │              │  ├─ AuthController.java
   │  │              │  ├─ PetProfileController.java
   │  │              │  ├─ TestController.java
   │  │              │  └─ UserController.java
   │  │              └─ dto
   │  │                 ├─ ApiResponse.java
   │  │                 ├─ PetProfileDto.java
   │  │                 └─ user
   │  │                    ├─ UserDto.java
   │  │                    ├─ UserLoginDto.java
   │  │                    ├─ UserRegistrationDto.java
   │  │                    └─ UserResponseDto.java
   │  └─ resources
   │     └─ application.properties
   └─ test
      └─ java
         └─ com
            └─ example
               └─ plog
                  └─ PlogApplicationTests.java

```
