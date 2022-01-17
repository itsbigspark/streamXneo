Docker Installation

1) Create the docker compose file attached.
2) Create named volumes and bind them to your local repository.
	docker volume create -d local -o o=bind -o type=none -o device="path/to/docker_volumes" neo-data
	docker volume create -d local -o o=bind -o type=none -o device="path/to/docker_volumes" neo-logs
	docker volume create -d local -o o=bind -o type=none -o device="path/to/docker_volumes" sdc-data
	docker volume create -d local -o o=bind -o type=none -o device="path/to/docker_volumes" sdc-stagelibs 
3) Docker compose will allow you to run both the apps on same network.
4) Go to the folder with docker-compose.yml file.
5) Use the below command.
	docker-compose up
6) You can go to browser and use the apps.

Installing staging libraries in streamsets

1) Use the below command to install the staging libraries and restart the container.
	docker run --rm -v sdc-stagelibs:/opt/streamsets-datacollector-3.20.0/streamsets-libs streamsets/datacollector:3.20.0 stagelibs -install=streamsets-datacollector-aws-lib
2) Alternate way is to install the libraries from the SDC.

Adding external libraries

1) External libraries can be uploaded to volume sdc-stagelibs (local bind).
2) Alternate way is to install from SDC. The external libraries are added to a different location. /opt/streamsets-datacollector-3.20.0/streamsets-libs-extras 