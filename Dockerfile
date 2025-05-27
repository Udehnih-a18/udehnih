# ===========================
# 1️⃣ Build Stage (Compiles the JAR)
# ===========================
FROM docker.io/library/eclipse-temurin:21-jdk-alpine AS builder

# Set working directory
WORKDIR /src/eshop

# Copy project files into container
COPY . .

RUN chmod +x ./gradlew

# Build the JAR file using Gradle
RUN ./gradlew clean bootJar

# Debug: List JAR files
RUN ls -la build/libs/

# ===========================
# 2️⃣ Run Stage (Creates a lightweight container)
# ===========================
FROM docker.io/library/eclipse-temurin:21-jre-alpine AS runner

# Set up a non-root user
ARG USER_NAME=eshop
ARG USER_UID=1000
ARG USER_GID=${USER_UID}

RUN addgroup -g ${USER_GID} ${USER_NAME} \
    && adduser -h /opt/eshop -D -u ${USER_UID} -G ${USER_NAME} ${USER_NAME}

# Switch to non-root user
USER ${USER_NAME}

# Set the working directory inside the container
WORKDIR /opt/eshop

# Copy the JAR file from the builder stage
COPY --from=builder --chown=${USER_UID}:${USER_GID} /src/eshop/build/libs/*.jar app.jar

# Debug: Verify JAR file exists and is executable
RUN ls -la app.jar

# Expose the application port
EXPOSE 8080

# Add health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Run the application with proper JVM options
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]