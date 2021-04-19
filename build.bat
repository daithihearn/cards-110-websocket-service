@ECHO off

ECHO "Building image websocket-service"
CALL docker build -t localhost:5000/websocket-service:latest .
CALL docker push localhost:5000/websocket-service:latest