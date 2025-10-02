# Recording Query Service - API 사용 예시

## 환경 설정

기본 URL: `http://localhost:8081`

## 1. 출석 기록 조회 API

### 사용자별 출석 기록 조회
```bash
curl -X GET "http://localhost:8081/api/query/attendance?userId=550e8400-e29b-41d4-a716-446655440000"
```

**응답 예시:**
```json
[
  {
    "id": "64a1b2c3d4e5f6789012345a",
    "recordId": 123,
    "userId": "550e8400-e29b-41d4-a716-446655440000",
    "sessionId": "session-abc-123",
    "attendanceTime": "2024-08-21T09:00:00",
    "status": "PRESENT",
    "createdAt": "2024-08-21T09:00:00",
    "updatedAt": "2024-08-21T09:00:00"
  }
]
```

### 세션별 출석 기록 조회
```bash
curl -X GET "http://localhost:8081/api/query/attendance?sessionId=session-abc-123"
```

### 출석 기록 ID로 조회
```bash
curl -X GET "http://localhost:8081/api/query/attendance/64a1b2c3d4e5f6789012345a"
```

### 사용자 + 세션 복합 조회
```bash
curl -X GET "http://localhost:8081/api/query/attendance?userId=550e8400-e29b-41d4-a716-446655440000&sessionId=session-abc-123"
```

---

## 2. 데이팅 참여 기록 조회 API

### 사용자별 데이팅 참여 기록
```bash
curl -X GET "http://localhost:8081/api/query/dating-participations?userId=550e8400-e29b-41d4-a716-446655440000"
```

**응답 예시:**
```json
[
  {
    "id": "64a1b2c3d4e5f6789012345b",
    "recordId": 456,
    "datingGroupId": "660e8400-e29b-41d4-a716-446655440001",
    "userId": "550e8400-e29b-41d4-a716-446655440000",
    "meetingId": "770e8400-e29b-41d4-a716-446655440002",
    "eventName": "Spring Dating Meetup",
    "scheduledAt": "2024-08-21T19:00:00",
    "meetingCreatedAt": "2024-08-15T10:00:00",
    "recordedAt": "2024-08-21T19:05:00"
  }
]
```

### 데이팅 그룹별 참여 기록
```bash
curl -X GET "http://localhost:8081/api/query/dating-participations?datingGroupId=660e8400-e29b-41d4-a716-446655440001"
```

### 미팅별 참여 기록
```bash
curl -X GET "http://localhost:8081/api/query/dating-participations?meetingId=770e8400-e29b-41d4-a716-446655440002"
```

### 데이팅 참여 기록 ID로 조회
```bash
curl -X GET "http://localhost:8081/api/query/dating-participations/64a1b2c3d4e5f6789012345b"
```

---

## 3. 스터디 참여 기록 조회 API

### 사용자별 스터디 참여 기록
```bash
curl -X GET "http://localhost:8081/api/query/study-participations?userId=550e8400-e29b-41d4-a716-446655440000"
```

**응답 예시:**
```json
[
  {
    "id": "64a1b2c3d4e5f6789012345c",
    "recordId": 789,
    "studyGroupId": "880e8400-e29b-41d4-a716-446655440003",
    "userId": "550e8400-e29b-41d4-a716-446655440000",
    "meetingId": "990e8400-e29b-41d4-a716-446655440004",
    "studyGroupName": "Java Study Group",
    "meetingName": "Week 5: Spring Security",
    "completedAt": "2024-08-21T16:00:00",
    "recordedAt": "2024-08-21T16:05:00"
  }
]
```

### 스터디 그룹별 참여 기록
```bash
curl -X GET "http://localhost:8081/api/query/study-participations?studyGroupId=880e8400-e29b-41d4-a716-446655440003"
```

### 미팅별 참여 기록
```bash
curl -X GET "http://localhost:8081/api/query/study-participations?meetingId=990e8400-e29b-41d4-a716-446655440004"
```

### 스터디 참여 기록 ID로 조회
```bash
curl -X GET "http://localhost:8081/api/query/study-participations/64a1b2c3d4e5f6789012345c"
```

---

## 4. 통합 조회 API

### 사용자의 모든 참여 기록 통합 조회
```bash
curl -X GET "http://localhost:8081/api/query/users/550e8400-e29b-41d4-a716-446655440000/participations"
```

**응답 예시:**
```json
{
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "attendanceRecords": [
    {
      "id": "64a1b2c3d4e5f6789012345a",
      "recordId": 123,
      "userId": "550e8400-e29b-41d4-a716-446655440000",
      "sessionId": "session-abc-123",
      "attendanceTime": "2024-08-21T09:00:00",
      "status": "PRESENT",
      "createdAt": "2024-08-21T09:00:00",
      "updatedAt": "2024-08-21T09:00:00"
    }
  ],
  "datingParticipations": [
    {
      "id": "64a1b2c3d4e5f6789012345b",
      "recordId": 456,
      "datingGroupId": "660e8400-e29b-41d4-a716-446655440001",
      "userId": "550e8400-e29b-41d4-a716-446655440000",
      "meetingId": "770e8400-e29b-41d4-a716-446655440002",
      "eventName": "Spring Dating Meetup",
      "scheduledAt": "2024-08-21T19:00:00",
      "meetingCreatedAt": "2024-08-15T10:00:00",
      "recordedAt": "2024-08-21T19:05:00"
    }
  ],
  "studyParticipations": [
    {
      "id": "64a1b2c3d4e5f6789012345c",
      "recordId": 789,
      "studyGroupId": "880e8400-e29b-41d4-a716-446655440003",
      "userId": "550e8400-e29b-41d4-a716-446655440000",
      "meetingId": "990e8400-e29b-41d4-a716-446655440004",
      "studyGroupName": "Java Study Group",
      "meetingName": "Week 5: Spring Security",
      "completedAt": "2024-08-21T16:00:00",
      "recordedAt": "2024-08-21T16:05:00"
    }
  ]
}
```

---

## 5. Health Check

```bash
curl -X GET "http://localhost:8081/api/query/health"
```

**응답 예시:**
```json
{
  "status": "UP",
  "service": "recording-query-service"
}
```

---

## Postman Collection

### 환경 변수 설정
```json
{
  "baseUrl": "http://localhost:8081",
  "userId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### 예시 요청들
1. **Get All Attendance**
   - Method: GET
   - URL: `{{baseUrl}}/api/query/attendance`

2. **Get Attendance by User**
   - Method: GET
   - URL: `{{baseUrl}}/api/query/attendance?userId={{userId}}`

3. **Get User All Participations**
   - Method: GET
   - URL: `{{baseUrl}}/api/query/users/{{userId}}/participations`

---

## MongoDB 직접 조회

### MongoDB Shell에서 데이터 확인
```bash
mongosh recording_query_db

# 출석 기록 조회
db.attendance_records_query.find().pretty()

# 특정 사용자의 출석 기록
db.attendance_records_query.find({ userId: "550e8400-e29b-41d4-a716-446655440000" }).pretty()

# 데이팅 참여 기록 조회
db.dating_participations_query.find().pretty()

# 스터디 참여 기록 조회
db.study_participations_query.find().pretty()

# 인덱스 확인
db.attendance_records_query.getIndexes()
```

---

## Kafka 이벤트 수신 테스트

### Kafka Producer로 테스트 이벤트 발행
```bash
# 출석 기록 생성 이벤트
kafka-console-producer --broker-list localhost:9092 --topic grewmeet.recording.attendance.created
{"id":123,"userId":"550e8400-e29b-41d4-a716-446655440000","sessionId":"session-abc-123","attendanceTime":"2024-08-21T09:00:00","status":"PRESENT","createdAt":"2024-08-21T09:00:00"}

# 데이팅 참여 기록 이벤트
kafka-console-producer --broker-list localhost:9092 --topic grewmeet.recording.dating-participation
{"id":456,"datingGroupId":"660e8400-e29b-41d4-a716-446655440001","userId":"550e8400-e29b-41d4-a716-446655440000","meetingId":"770e8400-e29b-41d4-a716-446655440002","eventName":"Spring Dating Meetup","scheduledAt":"2024-08-21T19:00:00","meetingCreatedAt":"2024-08-15T10:00:00","recordedAt":"2024-08-21T19:05:00"}

# 스터디 참여 기록 이벤트
kafka-console-producer --broker-list localhost:9092 --topic grewmeet.recording.study-participation
{"id":789,"studyGroupId":"880e8400-e29b-41d4-a716-446655440003","userId":"550e8400-e29b-41d4-a716-446655440000","meetingId":"990e8400-e29b-41d4-a716-446655440004","studyGroupName":"Java Study Group","meetingName":"Week 5: Spring Security","completedAt":"2024-08-21T16:00:00","recordedAt":"2024-08-21T16:05:00"}
```

---

## 문제 해결

### MongoDB 연결 실패
```bash
# MongoDB가 실행 중인지 확인
docker ps | grep mongo

# MongoDB 로그 확인
docker logs recording-query-mongodb
```

### Kafka 연결 실패
```bash
# Kafka가 실행 중인지 확인
docker ps | grep kafka

# Kafka 로그 확인
docker logs recording-kafka
```

### 애플리케이션 로그 확인
```bash
# 로그 레벨이 DEBUG로 설정되어 있어 상세한 로그가 출력됩니다
tail -f logs/spring.log
```
