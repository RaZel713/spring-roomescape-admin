# 방탈출 예약 관리 1단계 ~ 3단계 : 페어 프로그래밍

## 🚀 1단계 - 홈 화면

- `localhost:8080` 요청 시 welcome 페이지가 응답할 수 있도록 구현한다.
    - welcome 페이지는 `static/index.html` 파일을 이용한다.


- `localhost:8080/admin`요청 시 어드민 메인 페이지가 응답할 수 있도록 구현한다.
    - 어드민 메인 페이지는 `templates/admin/index.html` 파일을 이용한다.

## 🚀 2단계 - 예약 조회

- `/admin/reservation`요청 시 예약 관리 페이지가 응답할 수 있도록 구현한다.
    - 예약 관리 페이지는 `templates/admin/reservation-legacy.html`파일을 이용한다.


- API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API를 구현한다.

## 🚀 3단계 - 예약 추가 / 취소

- API 명세를 따라 예약 추가 API 와 삭제 API를 구현한다.

# 방탈출 예약 관리 4단계 ~ 9단계 : 개별 진행

- 4~9단계에서는 레벨1에서 학습했던 Junit만 활용해 단위 테스트를 작성한다.
    - 요구사항에서 RestAssured를 활용한 테스트가 주어진 경우 그대로 사용하고, RestAssured 기반의 테스트 코드를 더 발전 시키지 않늗다.
    - 대신 Junit을 활용해 단위 테스트에 집중한다.

## 🚀 4단계 - 데이터베이스 적용하기

- [x] build.gradle 파일을 이용하여 `spring-boot-stater-jdbc`, `h2` 두 의존성을 추가한다.
- [x] 데이터베이스 테이블 생성을 위해 `resources/schema.sql` 파일을 생성하고 예약 테이블을 생성하는 쿼리를 작성한다.
- [x] h2 데이터베이스의 console 기능을 활성화하고, datasource url을 `jdbc:h2:mem:database`로 지정한다.

- [x] 테스트) JdbcTemplate을 이용하여 DataSource객체에 접근하기
- [x] 테스트) DataSource 객체를 이용하여 Connection 확인하기
- [x] 테스트) Connection 객체를 이용하여 데이터베이스 이름 검증
- [x] 테스트) Connection 객체를 이용하여 테이블 이름 검증
