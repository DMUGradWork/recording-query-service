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
@Document(collection = "attendance_records_query")
public class AttendanceRecordQuery {
    
    @Id
    private String id;  // MongoDB uses String for _id
    
    private Long recordId;  // Original ID from command service
    
    @Indexed
    private String userId;
    
    @Indexed
    private String sessionId;
    
    private LocalDateTime attendanceTime;
    
    private AttendanceStatus status;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    public enum AttendanceStatus {
        PRESENT,
        ABSENT,
        LATE,
        EXCUSED
    }
}
