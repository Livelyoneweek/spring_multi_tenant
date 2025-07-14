# Spring Boot 멀티 테넌트 예제 (Schema-per-Tenant 방식)

이 프로젝트는 Spring Boot와 JPA(Hibernate)를 사용하여 **스키마 분리(Schema-per-Tenant)** 방식의 멀티 테넌트 아키텍처를 구현한 예제입니다.

## 📖 프로젝트 개요

멀티 테넌시(Multi-tenancy)는 단일 애플리케이션 인스턴스로 여러 테넌트(고객)에게 서비스를 제공하는 아키텍처입니다. 이 프로젝트는 그중에서도 각 테넌트가 독립된 데이터베이스 스키마를 갖는 **Schema-per-Tenant** 전략을 사용합니다.

-   **요청 흐름:**
    1.  클라이언트는 HTTP 요청 시 Header에 `X-TenantID` 값을 포함하여 API를 호출합니다.
    2.  `TenantInterceptor`가 요청을 가로채 `X-TenantID`를 추출하고, `ThreadLocal`에 테넌트 ID를 저장합니다.
    3.  JPA(Hibernate)가 데이터베이스 연결을 요청하면, `SchemaBasedMultiTenantConnectionProvider`가 현재 `ThreadLocal`의 테넌트 ID를 기반으로 데이터베이스 스키마(search_path)를 동적으로 변경합니다.
    4.  해당 요청이 끝날 때까지의 모든 쿼리는 지정된 테넌트의 스키마에서 실행되어 데이터 격리를 보장합니다.

## 🛠️ 주요 기술 스택

-   **언어:** Java 17
-   **프레임워크:** Spring Boot 3.3.5
-   **데이터베이스:** PostgreSQL
-   **ORM:** Spring Data JPA / Hibernate
-   **빌드 도구:** Gradle
-   **API 문서:** Springdoc OpenAPI (Swagger UI)
-   **기타:** Lombok, P6Spy (SQL 로깅)

## ⚙️ 멀티 테넌트 핵심 구현 클래스

-   `kr.co.mhnt.multi.mytenant.TenantInterceptor`:
    -   HTTP 요청 헤더(`X-TenantID`)에서 테넌트 ID를 읽어 `TenantContext`에 설정하는 인터셉터입니다.
-   `kr.co.mhnt.multi.mytenant.TenantContext`:
    -   `ThreadLocal`을 사용하여 현재 요청 스레드에서 사용할 테넌트 ID를 안전하게 관리합니다.
-   `kr.co.mhnt.multi.tenant.SchemaBasedMultiTenantConnectionProvider`:
    -   Hibernate의 `MultiTenantConnectionProvider`를 구현한 클래스입니다.
    -   `TenantContext`에서 현재 테넌트 ID를 가져와 PostgreSQL의 `SET search_path` 명령을 실행하여 해당 트랜잭션의 스키마를 동적으로 전환합니다.
-   `kr.co.mhnt.multi.tenant.TenantIdentifierResolver`:
    -   현재 테넌트 식별자를 확인하여 Hibernate에 제공합니다.

## 🚀 실행 방법

### 1. 사전 준비

-   JDK 17 설치
-   PostgreSQL 설치 및 실행

### 2. 데이터베이스 및 스키마 생성

PostgreSQL에 접속하여 공용 데이터베이스를 생성하고, 테스트할 테넌트들의 스키마를 미리 생성해야 합니다.

```sql
-- 예시: tenant1, tenant2 스키마 생성
CREATE SCHEMA tenant1;
CREATE SCHEMA tenant2;

-- 각 스키마에 user 테이블 생성
CREATE TABLE tenant1.users (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255)
);
CREATE TABLE tenant2.users (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255)
);

-- 테스트 데이터 삽입
INSERT INTO tenant1.users (id, name) VALUES (1, 'Tenant 1 User');
INSERT INTO tenant2.users (id, name) VALUES (1, 'Tenant 2 User');
```

### 3. 애플리케이션 설정

`src/main/resources/application.properties` 파일에 자신의 PostgreSQL 접속 정보를 입력합니다.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 4. 애플리케이션 실행

프로젝트 루트 디렉터리에서 아래 명령어를 실행합니다.

```bash
./gradlew bootRun
```

## 🔬 API 테스트 방법

애플리케이션 실행 후, `curl`이나 Postman 같은 도구를 사용하여 API를 테스트할 수 있습니다. `X-TenantID` 헤더 값에 따라 다른 스키마의 데이터를 조회하는 것을 확인할 수 있습니다.

**Tenant 1의 사용자 조회:**

```bash
curl -X GET http://localhost:8080/api/user/1 \
-H "X-TenantID: tenant1"
```

> **응답:** `Tenant 1 User`

**Tenant 2의 사용자 조회:**

```bash
curl -X GET http://localhost:8080/api/user/1 \
-H "X-TenantID: tenant2"
```

> **응답:** `Tenant 2 User`

---

Swagger UI에서도 API 테스트가 가능합니다.
-   **Swagger UI 주소:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)