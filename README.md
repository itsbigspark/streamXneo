# StreamsetsXNeo4j
StreamsetsXNeo4j consists of custom stages to build a pipeline using Neo4j.

### Getting started

1. Clone project
2. Install [Streamsets](https://streamsets.com/getting-started/download-install-data-collector/)
    - Ensure permissions [logging permissions](https://docs.streamsets.com/portal/#datacollector/latest/help/datacollector/UserGuide/Configuration/CustomStageLibraries.html) are enabled
3. Add Streamsets to PATH
```
STREAMSETS_HOME='path_to_streamsets/bin:'
export PATH=$STREAMSETS_HOME$PATH
```
4. Install [Neo4j](https://neo4j.com/download/)
5. Install [Java](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)
6. Install [Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)

### Add custom stages to streamsets
1. Build project using 

```
mvn clean package -DskipTests -DtargetDir=/path_to_streamsets/streamsets-datacollector-3.22.3/user-libs
```
2. Run streamsets launch commmand in terminal
```
streamsets dc
```


