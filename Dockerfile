# Backend Dockerfile
FROM eclipse-temurin:21-jre
VOLUME /tmp
COPY build/libs/*.jar app.jar
# -XX:-UseBASE64Intrinsics: Java4Shell(CVE-2026-114938) 임시 완화 옵션
# eclipse-temurin:21-jre가 21.0.13.1 이상으로 갱신된 것을 확인하면 제거
ENTRYPOINT ["java", "-XX:-UseBASE64Intrinsics", "-jar", "/app.jar"]