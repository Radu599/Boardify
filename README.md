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
#### Remove all images
  docker rmi $(docker images -a -q)
#### Remove all unused local volumes
  docker volume prune
#### Display containers stats
docker stats $(docker ps --format={{.Names}})
  
#### If db containers fail starting, try to stop those containers and remove all unused local volumes!
#### How to access mysql db from docker container:
https://stackoverflow.com/questions/48105051/docker-how-to-take-a-look-at-the-tables-inside-mysql-volume

#### How do I kill the process currently using a port on localhost in Windows?
https://stackoverflow.com/questions/39632667/how-do-i-kill-the-process-currently-using-a-port-on-localhost-in-windows

