Camel + Kafka + Akka example in Play
=================================

### Creating docker image

```shell
sbt docker:publishLocal
```
or, to publish images to docker.io:

```shell
# will need to do docker login first
docker:publish
```


### Running containers

```
docker run -d --name zookeeper jplock/zookeeper:3.4.6
docker run -d --name kafka --link zookeeper:zookeeper ches/kafka

# Make the new Kafka topic
ZK_IP=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' zookeeper)
docker run --rm ches/kafka kafka-topics.sh --create --topic test --replication-factor 1 --partitions 1 --zookeeper $ZK_IP:2181

docker run -d --name camel-scala -p 9000:9000 --link zookeeper:zookeeper --link kafka:kafka camel-scala:0.1-SNAPSHOT
```
