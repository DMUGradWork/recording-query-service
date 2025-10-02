package com.grewmeet.recording.recordingqueryservice.controller;

import com.grewmeet.recording.recordingqueryservice.domain.AttendanceRecordQuery;
import com.grewmeet.recording.recordingqueryservice.domain.DatingParticipationQuery;
import com.grewmeet.recording.recordingqueryservice.domain.StudyParticipationQuery;
import com.grewmeet.recording.recordingqueryservice.service.RecordingQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/query")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.features.mongodb.enabled", havingValue = "true", matchIfMissing = false)
public class RecordingQueryController {
    
    private final RecordingQueryService queryService;
    
    // ============ Attendance Record Endpoints ============
    
    @GetMapping("/attendance")
    public ResponseEntity<List<AttendanceRecordQuery>> getAttendanceRecords(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String sessionId) {
        
        log.info("GET /api/query/attendance - userId: {}, sessionId: {}", userId, sessionId);
        
        List<AttendanceRecordQuery> records;
        
        if (userId != null && sessionId != null) {
            records = queryService.getAttendanceByUserIdAndSessionId(userId, sessionId);
        } else if (userId != null) {
            records = queryService.getAttendanceByUserId(userId);
        } else if (sessionId != null) {
            records = queryService.getAttendanceBySessionId(sessionId);
        } else {
            records = queryService.getAllAttendance();
        }
        
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/attendance/{id}")
    public ResponseEntity<AttendanceRecordQuery> getAttendanceById(@PathVariable String id) {
        log.info("GET /api/query/attendance/{}", id);
        
        return queryService.getAttendanceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // ============ Dating Participation Endpoints ============
    
    @GetMapping("/dating-participations")
    public ResponseEntity<List<DatingParticipationQuery>> getDatingParticipations(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String datingGroupId,
            @RequestParam(required = false) String meetingId) {
        
        log.info("GET /api/query/dating-participations - userId: {}, datingGroupId: {}, meetingId: {}", 
                userId, datingGroupId, meetingId);
        
        List<DatingParticipationQuery> records;
        
        if (userId != null) {
            records = queryService.getDatingParticipationByUserId(userId);
        } else if (datingGroupId != null) {
            records = queryService.getDatingParticipationByDatingGroupId(datingGroupId);
        } else if (meetingId != null) {
            records = queryService.getDatingParticipationByMeetingId(meetingId);
        } else {
            records = queryService.getAllDatingParticipations();
        }
        
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/dating-participations/{id}")
    public ResponseEntity<DatingParticipationQuery> getDatingParticipationById(@PathVariable String id) {
        log.info("GET /api/query/dating-participations/{}", id);
        
        return queryService.getDatingParticipationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // ============ Study Participation Endpoints ============
    
    @GetMapping("/study-participations")
    public ResponseEntity<List<StudyParticipationQuery>> getStudyParticipations(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String studyGroupId,
            @RequestParam(required = false) String meetingId) {
        
        log.info("GET /api/query/study-participations - userId: {}, studyGroupId: {}, meetingId: {}", 
                userId, studyGroupId, meetingId);
        
        List<StudyParticipationQuery> records;
        
        if (userId != null) {
            records = queryService.getStudyParticipationByUserId(userId);
        } else if (studyGroupId != null) {
            records = queryService.getStudyParticipationByStudyGroupId(studyGroupId);
        } else if (meetingId != null) {
            records = queryService.getStudyParticipationByMeetingId(meetingId);
        } else {
            records = queryService.getAllStudyParticipations();
        }
        
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/study-participations/{id}")
    public ResponseEntity<StudyParticipationQuery> getStudyParticipationById(@PathVariable String id) {
        log.info("GET /api/query/study-participations/{}", id);
        
        return queryService.getStudyParticipationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // ============ Unified User Participation Endpoint (Optional) ============
    
    @GetMapping("/users/{userId}/participations")
    public ResponseEntity<Map<String, Object>> getAllParticipationsByUserId(@PathVariable String userId) {
        log.info("GET /api/query/users/{}/participations", userId);
        
        Map<String, Object> participations = queryService.getAllParticipationsByUserId(userId);
        return ResponseEntity.ok(participations);
    }
    
    // ============ Health Check Endpoint ============
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "recording-query-service"));
    }
}
