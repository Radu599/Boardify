# Boardify

# Deploy
0. "mvn clean install" each module
1. Open terminal in root project
2. run: docker network create boardify-app-network
3. run: docker-compose up -d
To see logs use: docker logs [containerName] or run docker-compose without detaching (-d)

## Docker comands:
#### Stop all running containers:
  docker stop $(docker ps -aq) 
#### Remove all running containers:
  docker rm $(docker ps -aq)
#### Remove image by IMAGE ID
  docker rmi Image [IMAGE ID]
#### Remove all image
  docker rmi $(docker images -a -q)
#### Remove all unused local volumes
  docker volume prune
  
If db containers fail starting, try to stop those containers and remove all unused local volumes!
