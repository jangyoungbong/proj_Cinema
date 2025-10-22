(1) 프로젝트 주제

Cinema Reservation System (영화 예매 프로그램)

Java로 구현한 콘솔 기반 영화 예매 관리 시스템

Menu 인터페이스와 AbstractMenu 추상 클래스를 활용한 객체지향 구조 설계

movies.txt, reservations.txt 파일을 통한 영화 및 예매 정보의 입출력 관리

관리자 메뉴를 통한 영화 등록 / 목록 확인 / 삭제

사용자 메뉴를 통한 영화 예매 / 예매 확인 / 예매 취소

Seats 클래스를 이용한 좌석 현황 출력 및 중복 예매 방지 로직 구현


(2) 프로젝트 기간

2025.10.20(월) ~ 2025.10.22(수) 7교시 (2일)

1일차: 프로젝트 설계 및 클래스 구조 정의 (Menu, Movie, Reservation 등)

2일차: 파일 입출력 기능 구현, 메뉴 흐름 제어 및 오류 처리, 테스트 및 마무리

(3) 추가하고 싶은 기능 및 기술
구분	추가 아이디어	기술 포인트
- 관리자 로그인 강화	비밀번호 암호화 및 인증 시스템	java.security, 해시 기반 인증
- 데이터베이스 연동	파일 대신 DB(MySQL, SQLite)로 관리	JDBC, DAO 패턴
- GUI 버전 업그레이드	콘솔 대신 GUI로 직관적 예매 가능	JavaFX 또는 Swing
- 웹 전환 프로젝트	GitHub Pages or Spring Boot 기반 웹 예매 서비스	Spring Boot, JSP/Thymeleaf
- 좌석 예매 시각화	그래픽 좌석표로 선택 가능	JavaFX GridPane, Canvas
   통계 기능 추가	예매율, 인기 영화 등 시각화	Chart 라이브러리, 데이터 분석 로직

(4) 프로젝트 후 계획 (후기)

이번 프로젝트를 통해 단순한 콘솔 프로그램이 아니라
객체지향적 구조와 실제 서비스 로직의 연계성을 깊이 이해할 수 있었습니다.

특히

인터페이스 → 추상클래스 → 구현클래스의 계층적 구조 설계,

예외 처리 및 파일 기반 CRUD,

싱글톤 패턴(MainMenu, AdminMenu) 구현
등을 통해 실무적인 Java 프로그램 설계 패턴을 익힐 수 있었습니다.

(5)향후 계획

GUI 기반 영화 예매 시스템으로 확장

JDBC 연동을 통한 DB 저장 및 통계 기능 추가

GitHub Pages 및 Spring Boot 버전으로 웹 예매 서비스 구현
