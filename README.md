# StreamXNeo
StreamXNeo consists of custom stages to build a pipeline using Neo4j.

# Table of Contents

# Installation

### Local
1. Download [Streamsets Data Collector](https://streamsets.com/getting-started/download-install-data-collector/) : Version 3.22.3

2. Add Streamsets Data Collector to PATH
```
STREAMSETS_HOME='path_to_streamsets/bin:'
export PATH=$STREAMSETS_HOME$PATH
```

3. Install [Neo4j](https://neo4j.com/download/) : Version 4.2.1 
   
4. Install [Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)


### Docker
1. Create that docker-compose.yml is in project directory
2. Create named volumes and bind them to your local repository.
```
docker volume create -d local -o o=bind -o type=none -o device="path_to_store_docker_volumes" neo-data
docker volume create -d local -o o=bind -o type=none -o device="path_to_store_docker_volumes" neo-logs
docker volume create -d local -o o=bind -o type=none -o device="path_to_store_docker_volumes" sdc-data
docker volume create -d local -o o=bind -o type=none -o device="path_to_store_docker_volumes" sdc-stagelibs 
```

3. Docker compose will allow you to run both the apps on same network.
4. Go to the folder with docker-compose.yml file.
5. Use the below command.
```
docker-compose up
```
# Usage 
### Local 

1. Ensure that [permissions](https://docs.streamsets.com/portal/#datacollector/latest/help/datacollector/UserGuide/Configuration/CustomStageLibraries.html) for running custom stages in streamsets  are enabled in $SDC_CONF/sdc-security.policy
```
permission java.io.FilePermission "/etc/os-release", "read";

permission java.io.FilePermission "/usr/lib/os-release", "read";

permission java.util.PropertyPermission "*", "read,write";

```

3. Download the most recent jar from the [assets](https://github.com/itsbigspark/streamXneo/packages/887595) section

4. Copy jar file to /path_to_streamsets/user_lib/streamxneo/lib

5. Increase open file limit 
```
ulimit -n 32768
```

6. Start streamsets in terminal
```
streamsets dc

```

7. Open Streamsets in browser :  http://localhost:18630 

8. Open Neo4j in browser : http://localhost:7474/ 
   - Login in using the bolt driver   

### Docker 
1. Use the below command to install the staging libraries and restart the container.
```
docker run --rm -v sdc-stagelibs:/opt/streamsets-datacollector-3.20.0/streamsets-libs streamsets/datacollector:3.20.0 stagelibs -install=streamsets-datacollector-aws-lib
```
# Contributing

# Credits

# License
