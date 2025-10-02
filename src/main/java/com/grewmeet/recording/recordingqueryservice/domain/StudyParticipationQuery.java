package com.grewmeet.recording.recordingqueryservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "study_participations_query")
public class StudyParticipationQuery {
    
    @Id
    private String id;  // MongoDB uses String for _id
    
    private Long recordId;  // Original ID from command service
    
    @Indexed
    private String studyGroupId;
    
    @Indexed
    private String userId;
    
    @Indexed
    private String meetingId;
    
    private String studyGroupName;
    
    private String meetingName;
    
    private LocalDateTime completedAt;
    
    private LocalDateTime recordedAt;
}
