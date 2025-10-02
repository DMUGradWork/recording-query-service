package com.grewmeet.recording.recordingqueryservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grewmeet.recording.recordingqueryservice.domain.StudyParticipationQuery;
import com.grewmeet.recording.recordingqueryservice.event.dto.StudyParticipationRecorded;
import com.grewmeet.recording.recordingqueryservice.repository.StudyParticipationQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.features.kafka.enabled", havingValue = "true", matchIfMissing = false)
public class StudyParticipationEventListener {
    
    private final StudyParticipationQueryRepository repository;
    private final ObjectMapper objectMapper;
    
    @KafkaListener(topics = "grewmeet.recording.study-participation", groupId = "${spring.kafka.consumer.group-id}")
    public void handleStudyParticipationRecorded(String message) {
        try {
            log.info("Received StudyParticipationRecorded event: {}", message);
            
            StudyParticipationRecorded event = objectMapper.readValue(message, StudyParticipationRecorded.class);
            
            // Check if record already exists (idempotency)
            Optional<StudyParticipationQuery> existing = repository.findByRecordId(event.getId());
            if (existing.isPresent()) {
                log.info("Study participation already exists, skipping: recordId={}", event.getId());
                return;
            }
            
            StudyParticipationQuery query = StudyParticipationQuery.builder()
                    .recordId(event.getId())
                    .studyGroupId(event.getStudyGroupId())
                    .userId(event.getUserId())
                    .meetingId(event.getMeetingId())
                    .studyGroupName(event.getStudyGroupName())
                    .meetingName(event.getMeetingName())
                    .completedAt(event.getCompletedAt())
                    .recordedAt(event.getRecordedAt())
                    .build();
            
            repository.save(query);
            log.info("Saved study participation to query database: recordId={}", event.getId());
            
        } catch (Exception e) {
            log.error("Error processing StudyParticipationRecorded event: {}", message, e);
        }
    }
}
