all: clean

clean:
	gradle clean

compile:
	gradle build

test:
	gradle test

jar: 
	gradle build -x test
	gradle bootJar

run: jar
	echo Starting Spring at:  http://localhost:8080
	java -jar build/libs/starbucks-api-1.0.jar

# Docker

docker-build: jar
	docker build -t starbucks-api .
	docker images

docker-run: docker-build
	docker run --name starbucks-api -td -p 80:8080 starbucks-api	
	docker ps

docker-clean:
	docker stop starbucks-api
	docker rm starbucks-api
	docker rmi starbucks-api	

docker-shell:
	docker exec -it spring-gumball bash 

docker-push:
	docker login
	docker build -t ajitjakasania/starbucks-api:v1 -t ajitjakasania/starbucks-api:v1 .
	docker push ajitjakasania/starbucks-api:v1

# Compose

network-ls:
	docker network ls 

network-create:
	docker network create --driver bridge starbucks-api-v1_network

network-prune:
	docker network prune

compose-up:
	docker-compose up --scale starbucks=2 -d

compose-down:
	docker-compose down 	

lb-stats:
	echo "user = admin | password = admin"
	open http://localhost:1936

lb-test:
	open http://localhost


# Pod

pod-run:
	kubectl apply -f pod.yaml

pod-list:
	kubectl get pods

pod-desc:
	kubectl describe pods starbucks-api

pod-delete:
	kubectl delete -f pod.yaml

pod-shell:
	kubectl exec -it starbucks-api -- /bin/bash

pod-logs:
	kubectl logs -f spring-gumball

# Deployment

deployment-create:
	kubectl create -f deployment.yaml --save-config 

deployment-get:
	kubectl get deployments

deployment-get-pods:
	kubectl get pods -l name=starbucks-api

deployment-pod-shell:
	kubectl exec -it $(pod) -- /bin/bash

deployment-upgrade:
	kubectl apply  -f deployment.yaml

deployment-delete:
	kubectl delete deployment starbucks-api-deployment

# Service

service-create:
	kubectl create -f service.yaml

service-get:
	kubectl get services

service-get-ip:
	kubectl get service starbucks-api -o wide

service-delete:
	kubectl delete service starbucks-api

# Ingress

ingress-apply:
	kubectl apply -f ingress.yaml

ingress-ip:
	kubectl get ingress starbucks-api-ingress




