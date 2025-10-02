package com.grewmeet.recording.recordingqueryservice.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatingParticipationRecorded {
    
    private Long id;
    private String datingGroupId;
    private String userId;
    private String meetingId;
    private String eventName;
    private LocalDateTime scheduledAt;
    private LocalDateTime meetingCreatedAt;
    private LocalDateTime recordedAt;
}
