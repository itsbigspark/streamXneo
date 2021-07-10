# StreamXNeo
StreamXNeo consists of custom stages to build a pipeline using Neo4j.

### Getting started (Local Installation)

1. Clone project
2. Install [Streamsets](https://streamsets.com/getting-started/download-install-data-collector/) : streamsets-datacollector-3.22.3
    - Ensure permissions [logging permissions](https://docs.streamsets.com/portal/#datacollector/latest/help/datacollector/UserGuide/Configuration/CustomStageLibraries.html) are enabled
    - Increase your open file limit
```
ulimit -n 32768
```
3. Add Streamsets to PATH
```
STREAMSETS_HOME='path_to_streamsets/bin:'
export PATH=$STREAMSETS_HOME$PATH
```
4. Install [Neo4j](https://neo4j.com/download/) : Version 4.2.1
   - Login in using the bolt driver    
5. Install [Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)

### Add custom stages to streamsets
1. Build project using 

```
mvn clean package -DskipTests -DtargetDir=/path_to_streamsets/streamsets-datacollector-3.22.3/user-libs
```
2. Run streamsets launch commmand in terminal
```
streamsets dc
```
### Getting started (Docker Installation)

1. Install [docker](https://docs.docker.com/get-docker/)
2. Follow instructions in DOCKER-README.txt

### Using JAR package

To use JAR package download the recent version and rename it streamxneo-1.0-SNAPSHOT.jar