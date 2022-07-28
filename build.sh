./mvnw clean
./mvnw package

docker build -t revature/revazon .

docker run -p 8080:8080 revature/revazon