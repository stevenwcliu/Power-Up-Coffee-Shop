all: clean

clean:
	gradle clean

compile:
	gradle build -x test

jar: compile
	gradle bootJar

run: jar
	echo Starting Spring at:  http://localhost:8080
	java -jar build/libs/demo-docker-1.0.jar

# Docker

docker-build: jar
	docker build -t spring-online-store .
	docker images

docker-run: docker-build
	docker run --name spring-online-store -td -p 80:8080 spring-online-store	
	docker ps

docker-clean:
	docker stop spring-online-store
	docker rm spring-online-store
	docker rmi spring-online-store

docker-shell:
	docker exec -it spring-online-store bash 

docker-push:
	docker login
	docker build -t $(account)/spring-online-store:v1.0 -t $(account)/spring-online-store:v1.0 .
	docker push $(account)/spring-online-store:v1.0
# Pod

pod-run:
	kubectl apply -f pod.yaml

pod-list:
	kubectl get pods

pod-desc:
	kubectl describe pods spring-online-store

pod-delete:
	kubectl delete -f pod.yaml

pod-shell:
	kubectl exec -it spring-online-store -- /bin/bash

pod-logs:
	kubectl logs -f spring-online-store

# Service

service-create:
	kubectl create -f service.yaml

service-get:
	kubectl get services

service-get-ip:
	kubectl get service spring-online-store -o wide

service-delete:
	kubectl delete service spring-online-store