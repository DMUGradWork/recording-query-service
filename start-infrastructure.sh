#!/bin/bash

echo "🚀 Recording Query Service - 인프라 시작"
echo "=================================="

# Docker Compose로 MongoDB와 Kafka 시작
echo "📦 MongoDB와 Kafka 컨테이너를 시작합니다..."
docker-compose up -d

echo ""
echo "⏳ 서비스가 시작될 때까지 대기 중..."
sleep 10

echo ""
echo "✅ 인프라 시작 완료!"
echo ""
echo "📊 실행 중인 서비스:"
echo "  - MongoDB: http://localhost:27017"
echo "  - Kafka: localhost:9092"
echo ""
echo "🔍 컨테이너 상태 확인:"
docker ps --filter "name=recording-"

echo ""
echo "💡 다음 명령어로 애플리케이션을 실행하세요:"
echo "   ./gradlew bootRun"
echo ""
echo "🛑 인프라를 중지하려면 다음 명령어를 실행하세요:"
echo "   docker-compose down"
