# AOPTest

- new - spring legacy project - spring mvc project
- project name: `AOPTest`
- root package: `com.test.aop`

---

#### 자바 버전과 pom.xml 수정

- 프로젝트 우클릭 -> Project Facets -> Java 11
- properties 태그에 있는 java-version 11
- 그 아래 스프링 버전 5.0.7.RELEASE로
- 맨 아래쪽 plugin 태그 -> maven plugin 내부
  - configuration 태그 내부 source, target 내부 11로 변경
- 의존성 추가 -> aop 관련 의존성 2개 추가

---

#### AOP 설정
- servlet-context.xml
  - aop를 어노테이션 방식으로 사용 가능하게 설정
- 

---

#### 파일, 패키지

##### src/main/java - Controller
- com.test.aop.controller
  - `MemoController.java`
- com.test.aop.service
  - `MemoService.java`
- com.test.aop.aspect
  - `Logger.java`

##### src/main/webapp - View
- WEB-INF/views
  - `list.jsp`
  - `add.jsp`
  - `view.jsp`