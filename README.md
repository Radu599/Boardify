# Boardify

# Deploy
0. Open terminal in root project
1. run: docker network create boardify-app-network
2. run: docker-compose up -d
To see logs use: docker logs [containerName] or run docker-compose without detaching (-d)

## Docker comands:
#### Stop all running containers:
  docker stop $(docker ps -aq) 
#### Remove all running containers:
  docker rm $(docker ps -aq)
Remove image by IMAGE ID
  docker rmi Image [IMAGE ID]
