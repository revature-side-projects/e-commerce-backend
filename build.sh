./mvnw clean
./mvnw package

docker build -t revature/revazon .

docker-compose up
