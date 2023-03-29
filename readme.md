# Using Kafka with a Java Producer and Consumer```

# Install Kafka as a container
Install docker to install Kafka container as a Linux container using the follwoing command:
docker run -it --name kafka-zkless -p 9092:9092 -e LOG_DIR=/tmp/logs quay.io/strimzi/kafka:latest-kafka-2.8.1-amd64 /bin/sh -c 'export CLUSTER_ID=$(bin/kafka-storage.sh random-uuid) && bin/kafka-storage.sh format -t $CLUSTER_ID -c config/kraft/server.properties && bin/kafka-server-start.sh config/kraft/server.properties'

# Starting the streaming producer

In a new terminal window, go to the directory in which this code is installed and execute the following command:

```shell
sh ./runproducer.sh "mytopic"
```

You see a steady stream of screen output that is the log output of messages being sent to the topic named `mytopic`.

# Starting an asynchronous consumer

In another terminal window, go to the directory in which this code is installed and execute the following command:

```shell
sh ./runconsumer.sh "mytopic"
```

You see a steady stream of screen output that is the log output of messages being retrieved from the topic named `mytopic`.

A sample of the output is as follows:

```
2022-05-31 09:45:53 DEBUG NetworkClient:521 - [Consumer clientId=consumer-test-group-1, groupId=test-group] Sending FETCH request with header RequestHeader(apiKey=FETCH, apiVersion=12, clientId=consumer-test-group-1, correlationId=86) and timeout 30000 to node 1: FetchRequestData(clusterId=null, replicaId=-1, maxWaitMs=500, minBytes=1, maxBytes=52428800, isolationLevel=0, sessionId=376433637, sessionEpoch=62, topics=[FetchTopic(topic='mytopic', topicId=vCvEuX_0QHWdVP5mKeeX4w, partitions=[FetchPartition(partition=0, currentLeaderEpoch=0, fetchOffset=787, lastFetchedEpoch=-1, logStartOffset=-1, partitionMaxBytes=1048576)])], forgottenTopicsData=[], rackId='')
{"bootstrapServers":"localhost:9092","topic":"mytopic","source":"com.demo.kafka.KafkaMessageHandlerImpl","message":"4GPeV7Igy9","key":"84097ac3-f488-4595-86cc-dcb69bce2eda"}
```


