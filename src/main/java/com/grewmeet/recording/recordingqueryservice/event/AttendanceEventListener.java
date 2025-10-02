package com.grewmeet.recording.recordingqueryservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grewmeet.recording.recordingqueryservice.domain.AttendanceRecordQuery;
import com.grewmeet.recording.recordingqueryservice.event.dto.AttendanceRecordCreated;
import com.grewmeet.recording.recordingqueryservice.event.dto.AttendanceRecordDeleted;
import com.grewmeet.recording.recordingqueryservice.event.dto.AttendanceRecordUpdated;
import com.grewmeet.recording.recordingqueryservice.repository.AttendanceRecordQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.features.kafka.enabled", havingValue = "true", matchIfMissing = false)
public class AttendanceEventListener {
    
    private final AttendanceRecordQueryRepository repository;
    private final ObjectMapper objectMapper;
    
    @KafkaListener(topics = "grewmeet.recording.attendance.created", groupId = "${spring.kafka.consumer.group-id}")
    public void handleAttendanceRecordCreated(String message) {
        try {
            log.info("Received AttendanceRecordCreated event: {}", message);
            
            AttendanceRecordCreated event = objectMapper.readValue(message, AttendanceRecordCreated.class);
            
            AttendanceRecordQuery query = AttendanceRecordQuery.builder()
                    .recordId(event.getId())
                    .userId(event.getUserId())
                    .sessionId(event.getSessionId())
                    .attendanceTime(event.getAttendanceTime())
                    .status(event.getStatus())
                    .createdAt(event.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            repository.save(query);
            log.info("Saved attendance record to query database: recordId={}", event.getId());
            
        } catch (Exception e) {
            log.error("Error processing AttendanceRecordCreated event: {}", message, e);
        }
    }
    
    @KafkaListener(topics = "grewmeet.recording.attendance.updated", groupId = "${spring.kafka.consumer.group-id}")
    public void handleAttendanceRecordUpdated(String message) {
        try {
            log.info("Received AttendanceRecordUpdated event: {}", message);
            
            AttendanceRecordUpdated event = objectMapper.readValue(message, AttendanceRecordUpdated.class);
            
            Optional<AttendanceRecordQuery> existingRecord = repository.findByRecordId(event.getId());
            
            if (existingRecord.isPresent()) {
                AttendanceRecordQuery query = existingRecord.get();
                query.setUserId(event.getUserId());
                query.setSessionId(event.getSessionId());
                query.setAttendanceTime(event.getAttendanceTime());
                query.setStatus(event.getStatus());
                query.setUpdatedAt(LocalDateTime.now());
                
                repository.save(query);
                log.info("Updated attendance record in query database: recordId={}", event.getId());
            } else {
                log.warn("Attendance record not found for update: recordId={}", event.getId());
                // Create new record if not found
                AttendanceRecordQuery query = AttendanceRecordQuery.builder()
                        .recordId(event.getId())
                        .userId(event.getUserId())
                        .sessionId(event.getSessionId())
                        .attendanceTime(event.getAttendanceTime())
                        .status(event.getStatus())
                        .createdAt(event.getCreatedAt())
                        .updatedAt(LocalDateTime.now())
                        .build();
                repository.save(query);
            }
            
        } catch (Exception e) {
            log.error("Error processing AttendanceRecordUpdated event: {}", message, e);
        }
    }
    
    @KafkaListener(topics = "grewmeet.recording.attendance.deleted", groupId = "${spring.kafka.consumer.group-id}")
    public void handleAttendanceRecordDeleted(String message) {
        try {
            log.info("Received AttendanceRecordDeleted event: {}", message);
            
            AttendanceRecordDeleted event = objectMapper.readValue(message, AttendanceRecordDeleted.class);
            
            repository.deleteByRecordId(event.getId());
            log.info("Deleted attendance record from query database: recordId={}", event.getId());
            
        } catch (Exception e) {
            log.error("Error processing AttendanceRecordDeleted event: {}", message, e);
        }
    }
}
