# Sử dụng hình ảnh Java chính thức từ Docker Hub
FROM openjdk:17
ADD target/springboot-mongo-docker.jar springboot-mongo-docker.jar

ENTRYPOINT ["java","-jar","/springboot-mongo-docker.jar"]
