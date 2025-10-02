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
@Document(collection = "dating_participations_query")
public class DatingParticipationQuery {
    
    @Id
    private String id;  // MongoDB uses String for _id
    
    private Long recordId;  // Original ID from command service
    
    @Indexed
    private String datingGroupId;
    
    @Indexed
    private String userId;
    
    @Indexed
    private String meetingId;
    
    private String eventName;
    
    private LocalDateTime scheduledAt;
    
    private LocalDateTime meetingCreatedAt;
    
    private LocalDateTime recordedAt;
}
