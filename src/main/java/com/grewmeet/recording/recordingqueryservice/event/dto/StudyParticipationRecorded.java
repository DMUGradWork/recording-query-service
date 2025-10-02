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
public class StudyParticipationRecorded {
    
    private Long id;
    private String studyGroupId;
    private String userId;
    private String meetingId;
    private String studyGroupName;
    private String meetingName;
    private LocalDateTime completedAt;
    private LocalDateTime recordedAt;
}
