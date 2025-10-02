package com.grewmeet.recording.recordingqueryservice.event.dto;

import com.grewmeet.recording.recordingqueryservice.domain.AttendanceRecordQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRecordCreated {
    
    private Long id;
    private String userId;
    private String sessionId;
    private LocalDateTime attendanceTime;
    private AttendanceRecordQuery.AttendanceStatus status;
    private LocalDateTime createdAt;
}
