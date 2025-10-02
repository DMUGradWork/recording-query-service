package com.grewmeet.recording.recordingqueryservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grewmeet.recording.recordingqueryservice.domain.DatingParticipationQuery;
import com.grewmeet.recording.recordingqueryservice.event.dto.DatingParticipationRecorded;
import com.grewmeet.recording.recordingqueryservice.repository.DatingParticipationQueryRepository;
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
public class DatingParticipationEventListener {
    
    private final DatingParticipationQueryRepository repository;
    private final ObjectMapper objectMapper;
    
    @KafkaListener(topics = "grewmeet.recording.dating-participation", groupId = "${spring.kafka.consumer.group-id}")
    public void handleDatingParticipationRecorded(String message) {
        try {
            log.info("Received DatingParticipationRecorded event: {}", message);
            
            DatingParticipationRecorded event = objectMapper.readValue(message, DatingParticipationRecorded.class);
            
            // Check if record already exists (idempotency)
            Optional<DatingParticipationQuery> existing = repository.findByRecordId(event.getId());
            if (existing.isPresent()) {
                log.info("Dating participation already exists, skipping: recordId={}", event.getId());
                return;
            }
            
            DatingParticipationQuery query = DatingParticipationQuery.builder()
                    .recordId(event.getId())
                    .datingGroupId(event.getDatingGroupId())
                    .userId(event.getUserId())
                    .meetingId(event.getMeetingId())
                    .eventName(event.getEventName())
                    .scheduledAt(event.getScheduledAt())
                    .meetingCreatedAt(event.getMeetingCreatedAt())
                    .recordedAt(event.getRecordedAt())
                    .build();
            
            repository.save(query);
            log.info("Saved dating participation to query database: recordId={}", event.getId());
            
        } catch (Exception e) {
            log.error("Error processing DatingParticipationRecorded event: {}", message, e);
        }
    }
}
