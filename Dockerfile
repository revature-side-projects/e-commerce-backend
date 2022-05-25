FROM openjdk

copy ./target/e-commerce-1.0.jar /workspace/e-commerce-1.0.jar

WORKDIR /workspace

EXPOSE 5000

ENTRYPOINT [ "java", "-jar", "e-commerce-1.0.jar" ]
