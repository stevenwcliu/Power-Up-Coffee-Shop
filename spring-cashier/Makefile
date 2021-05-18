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
	java -jar build/libs/spring-cashier-1.0.jar

# Docker

docker-build: jar
	docker build -t spring-cashier .
	docker images

docker-run: docker-build
	docker run --name spring-cashier -td -p 80:8080 spring-cashier	
	docker ps

docker-clean:
	docker stop spring-cashier
	docker rm spring-cashier
	docker rmi spring-cashier	

docker-shell:
	docker exec -it spring-cashier bash 

docker-push:
	docker login
	docker build -t ajitjakasania/spring-cashier:v1 -t ajitjakasania/spring-cashier:v1 .
	docker push ajitjakasania/spring-cashier:v1

# Compose

network-ls:
	docker network ls 

network-create:
	docker network create --driver bridge spring-cashier-v1_network

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
	kubectl describe pods spring-cashier

pod-delete:
	kubectl delete -f pod.yaml

pod-shell:
	kubectl exec -it spring-cashier -- /bin/bash

pod-logs:
	kubectl logs -f spring-cashier

# Deployment

deployment-create:
	kubectl create -f deployment.yaml --save-config 

deployment-get:
	kubectl get deployments

deployment-get-pods:
	kubectl get pods -l name=spring-cashier

deployment-pod-shell:
	kubectl exec -it $(pod) -- /bin/bash

deployment-upgrade:
	kubectl apply  -f deployment.yaml

deployment-delete:
	kubectl delete deployment spring-cashier-deployment

# Service

service-create:
	kubectl create -f service.yaml

service-get:
	kubectl get services

service-get-ip:
	kubectl get service spring-cashier -o wide

service-delete:
	kubectl delete service spring-cashier

# Ingress

ingress-apply:
	kubectl apply -f ingress.yaml

ingress-ip:
	kubectl get ingress spring-cashier-ingress




