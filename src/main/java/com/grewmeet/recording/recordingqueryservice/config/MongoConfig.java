package com.grewmeet.recording.recordingqueryservice.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ConditionalOnProperty(name = "app.features.mongodb.enabled", havingValue = "true", matchIfMissing = false)
@EnableMongoRepositories(basePackages = "com.grewmeet.recording.recordingqueryservice.repository")
@EnableMongoAuditing
public class MongoConfig {
    // MongoDB 기본 설정
    // application.yml에서 connection 정보 설정됨
    // app.features.mongodb.enabled=true일 때만 활성화
}
