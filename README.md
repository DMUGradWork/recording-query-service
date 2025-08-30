# Recording Query Service

기록 도메인의 조회 서비스 (출석 기록, 데이팅 서비스 참여, 스터디모임 참여 기록 조회 및 통계)

## 🔧 환경 설정
- **포트**: 8081 (Command Service와 구분)
- **데이터베이스**: H2 (인메모리) -> MVP 개발 후 MongoDB로 전환
- **Kafka**: localhost:9092

## 📡 이벤트 스키마
CQRS Query Side로서 Command Service에서 발행하는 이벤트를 수신하여 읽기 최적화된 데이터 모델 구성

### 🔽 수신 이벤트 (Command Service에서 발행)

<details>
<summary><strong>AttendanceRecordCreated</strong> - Command Service에서 수신</summary>

**Kafka Topic:** `grewmeet.recording.attendance`

**Record 클래스:**
```java
public record AttendanceRecordCreated(
    Long id,                        // 출석 기록 ID
    String userId,                  // 사용자 ID
    LocalDate attendanceDate,       // 출석 날짜
    Boolean isAttended,             // 출석 여부
    LocalDateTime checkInTime,      // 체크인 시간
    LocalDateTime recordedAt        // 기록 생성 시간
) {}
```

**예시 JSON:**
```json
{
  "id": 123,
  "userId": "user_123456789",
  "attendanceDate": "2024-08-21",
  "isAttended": true,
  "checkInTime": "2024-08-21T09:00:00",
  "recordedAt": "2024-08-21T09:00:00"
}
```
</details>

<details>
<summary><strong>DatingParticipationRecorded</strong> - Command Service에서 수신</summary>

**Kafka Topic:** `grewmeet.recording.dating-participation`

**Record 클래스:**
```java
public record DatingParticipationRecorded(
    Long id,                            // 참여 기록 ID
    String userId,                      // 사용자 ID
    String datingEventId,               // 데이팅 이벤트 ID
    LocalDateTime participationDate,    // 참여 날짜
    String status,                      // 참여 상태 ("PARTICIPATED")
    LocalDateTime recordedAt            // 기록 생성 시간
) {}
```

**예시 JSON:**
```json
{
  "id": 456,
  "userId": "user_123456789",
  "datingEventId": "dating_event_789",
  "participationDate": "2024-08-21T19:00:00",
  "status": "PARTICIPATED",
  "recordedAt": "2024-08-21T19:00:00"
}
```
</details>

<details>
<summary><strong>StudyParticipationRecorded</strong> - Command Service에서 수신</summary>

**Kafka Topic:** `grewmeet.recording.study-participation`

**Record 클래스:**
```java
public record StudyParticipationRecorded(
    Long id,                            // 참여 기록 ID
    String userId,                      // 사용자 ID
    String studyGroupId,                // 스터디 그룹 ID
    String sessionId,                   // 세션 ID
    LocalDateTime participationDate,    // 참여 날짜
    String status,                      // 참여 상태 ("ATTENDED")
    LocalDateTime recordedAt            // 기록 생성 시간
) {}
```

**예시 JSON:**
```json
{
  "id": 789,
  "userId": "user_123456789",
  "studyGroupId": "study_group_456",
  "sessionId": "session_123",
  "participationDate": "2024-08-21T14:00:00",
  "status": "ATTENDED",
  "recordedAt": "2024-08-21T14:00:00"
}
```
</details>

## 🚀 실행
```bash
./gradlew bootRun
```

## 📋 API 엔드포인트

### 출석 기록 조회
- **GET** `/attendance/users/{userId}/monthly?year={year}&month={month}` - 월별 출석 기록 조회
- **GET** `/attendance/users/{userId}/stats?startDate={date}&endDate={date}` - 기간별 출석 통계

### 데이팅 참여 기록 조회
- **GET** `/dating-participations/users/{userId}` - 데이팅 참여 기록 목록
- **GET** `/dating-participations/users/{userId}/stats` - 데이팅 참여 통계

### 스터디 참여 기록 조회
- **GET** `/study-participations/users/{userId}` - 스터디 참여 기록 목록
- **GET** `/study-participations/users/{userId}/stats` - 스터디 참여 통계

### 통합 대시보드
- **GET** `/dashboard/users/{userId}/summary` - 사용자 활동 종합 대시보드
- **GET** `/users/{userId}/qualification` - 데이팅 참여 자격 확인 (외부 서비스 연동용)

## 📄 API 문서
- Swagger UI: http://localhost:8081/swagger-ui.html
- H2 Console: http://localhost:8081/h2-console

## 🏗️ 아키텍처
- **CQRS Query Side** (읽기 전용, 조회 및 통계 최적화)
- **Event-Driven Architecture** (Kafka Consumer 기반 이벤트 수신)
- **Eventually Consistent** 데이터 모델
- **읽기 최적화된 데이터 구조** (비정규화, 집계 테이블 활용)

## 🔒 핵심 제약사항
- 데이터 수정 작업 금지 (조회 전용)
- Command Service 이벤트에 의존적인 데이터 동기화
- 멱등성 보장 (중복 이벤트 처리 방지)
- Eventually Consistent 데이터 모델

## 📊 데이터 모델

### 조회 최적화 모델
- **출석 조회 모델 (AttendanceQueryModel)**: 월별/기간별 출석 조회에 최적화
- **참여 기록 조회 모델 (ParticipationQueryModel)**: 데이팅/스터디 참여 이력 통합 관리
- **사용자 통계 모델**: 누적 참여 횟수 및 활동 지표

### 통계 데이터
- **출석률**: 기간별 출석 비율 계산
- **참여 횟수**: 데이팅/스터디 누적 참여 횟수
- **활동 점수**: 종합 활동 지표 (외부 서비스 연동용)

## 🎯 외부 서비스 연동
이 서비스는 다른 MSA 서비스에서 사용자 자격 확인을 위한 API를 제공합니다.

### 데이팅 서비스 연동
- 데이팅 이벤트 참여 자격 확인
- 최소 참여 횟수 기반 자격 검증
- 출석률 기반 신뢰도 평가

## 🛠️ 기술 스택
- **Java 21** & Spring Boot 3.5.5
- **Spring Data JPA** & H2 Database (개발용)
- **Apache Kafka Consumer** Event Processing
- **Lombok** 보일러플레이트 코드 감소
- **JUnit 5** Testing
- **Gradle** Build Management

## ⚡ 성능 최적화
- **읽기 전용 데이터베이스 연결** 사용
- **인덱스 최적화** (사용자 ID, 날짜 기반)
- **캐싱 전략** 자주 조회되는 통계 데이터