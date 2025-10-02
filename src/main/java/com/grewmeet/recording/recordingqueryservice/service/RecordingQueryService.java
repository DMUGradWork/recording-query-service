package com.grewmeet.recording.recordingqueryservice.service;

import com.grewmeet.recording.recordingqueryservice.domain.AttendanceRecordQuery;
import com.grewmeet.recording.recordingqueryservice.domain.DatingParticipationQuery;
import com.grewmeet.recording.recordingqueryservice.domain.StudyParticipationQuery;
import com.grewmeet.recording.recordingqueryservice.repository.AttendanceRecordQueryRepository;
import com.grewmeet.recording.recordingqueryservice.repository.DatingParticipationQueryRepository;
import com.grewmeet.recording.recordingqueryservice.repository.StudyParticipationQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.features.mongodb.enabled", havingValue = "true", matchIfMissing = false)
public class RecordingQueryService {
    
    private final AttendanceRecordQueryRepository attendanceRepository;
    private final DatingParticipationQueryRepository datingRepository;
    private final StudyParticipationQueryRepository studyRepository;
    
    // ============ Attendance Record Query Methods ============
    
    public List<AttendanceRecordQuery> getAttendanceByUserId(String userId) {
        log.debug("Querying attendance records for userId: {}", userId);
        return attendanceRepository.findByUserId(userId);
    }
    
    public List<AttendanceRecordQuery> getAttendanceBySessionId(String sessionId) {
        log.debug("Querying attendance records for sessionId: {}", sessionId);
        return attendanceRepository.findBySessionId(sessionId);
    }
    
    public List<AttendanceRecordQuery> getAttendanceByUserIdAndSessionId(String userId, String sessionId) {
        log.debug("Querying attendance records for userId: {} and sessionId: {}", userId, sessionId);
        return attendanceRepository.findByUserIdAndSessionId(userId, sessionId);
    }
    
    public Optional<AttendanceRecordQuery> getAttendanceById(String id) {
        log.debug("Querying attendance record by id: {}", id);
        return attendanceRepository.findById(id);
    }
    
    public List<AttendanceRecordQuery> getAllAttendance() {
        log.debug("Querying all attendance records");
        return attendanceRepository.findAll();
    }
    
    // ============ Dating Participation Query Methods ============
    
    public List<DatingParticipationQuery> getDatingParticipationByUserId(String userId) {
        log.debug("Querying dating participations for userId: {}", userId);
        return datingRepository.findByUserId(userId);
    }
    
    public List<DatingParticipationQuery> getDatingParticipationByDatingGroupId(String datingGroupId) {
        log.debug("Querying dating participations for datingGroupId: {}", datingGroupId);
        return datingRepository.findByDatingGroupId(datingGroupId);
    }
    
    public List<DatingParticipationQuery> getDatingParticipationByMeetingId(String meetingId) {
        log.debug("Querying dating participations for meetingId: {}", meetingId);
        return datingRepository.findByMeetingId(meetingId);
    }
    
    public Optional<DatingParticipationQuery> getDatingParticipationById(String id) {
        log.debug("Querying dating participation by id: {}", id);
        return datingRepository.findById(id);
    }
    
    public List<DatingParticipationQuery> getAllDatingParticipations() {
        log.debug("Querying all dating participations");
        return datingRepository.findAll();
    }
    
    // ============ Study Participation Query Methods ============
    
    public List<StudyParticipationQuery> getStudyParticipationByUserId(String userId) {
        log.debug("Querying study participations for userId: {}", userId);
        return studyRepository.findByUserId(userId);
    }
    
    public List<StudyParticipationQuery> getStudyParticipationByStudyGroupId(String studyGroupId) {
        log.debug("Querying study participations for studyGroupId: {}", studyGroupId);
        return studyRepository.findByStudyGroupId(studyGroupId);
    }
    
    public List<StudyParticipationQuery> getStudyParticipationByMeetingId(String meetingId) {
        log.debug("Querying study participations for meetingId: {}", meetingId);
        return studyRepository.findByMeetingId(meetingId);
    }
    
    public Optional<StudyParticipationQuery> getStudyParticipationById(String id) {
        log.debug("Querying study participation by id: {}", id);
        return studyRepository.findById(id);
    }
    
    public List<StudyParticipationQuery> getAllStudyParticipations() {
        log.debug("Querying all study participations");
        return studyRepository.findAll();
    }
    
    // ============ Unified User Participation Query (Optional) ============
    
    public Map<String, Object> getAllParticipationsByUserId(String userId) {
        log.debug("Querying all participations for userId: {}", userId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("attendanceRecords", attendanceRepository.findByUserId(userId));
        result.put("datingParticipations", datingRepository.findByUserId(userId));
        result.put("studyParticipations", studyRepository.findByUserId(userId));
        
        return result;
    }
}
