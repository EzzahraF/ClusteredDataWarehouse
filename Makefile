.PHONY: build run clean docker-build docker-run

build:
	mvn clean package -DskipTests

run:
	java -jar target/*.jar

clean:
	mvn clean

docker-build:
	docker build -t clustring-data-warehouse .

docker-run:
	docker run -d --name cdw-app --env-file .env -p 8080:8080 clustring-data-warehouse
