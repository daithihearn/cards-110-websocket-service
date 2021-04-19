#!/bin/sh

if [ -x "$(command -v docker)" ]; then
	echo "
	Building image websocket-service"
	docker build -t localhost:5000/websocket-service:latest .
	docker push localhost:5000/websocket-service:latest
else
    echo "Docker not installed"
fi