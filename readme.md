# Using Kafka with a Java Producer and Consumer
- [Install Java on RHEL 8](#install-java-on-rhel-8)
- [Install Maven on RHEL 8](#install-maven-on-rhel)
- [Install Podman on RHEL 8](#install-podman-on-rhel-8)
- [Install Kafka as a container](#install-kafka-as-a-container)
- [Running the unit tests](#running-the-unit-tests)
- [Starting the streaming producer](#starting-the-streaming-producer)
- [Starting an asynchronous consumer](#starting-an-asynchronous-consumer)
- [Open up the Kafka port using ufw](#open-up-the-kafka-port-using-the-ufw-utility)

# Install Java on RHEL 8 or Fedora
```shell
sudo yum install java-1.8.0-openjdk-devel
```
Verify the installation

```shell
java -version
```

```text
openjdk version "11.0.15" 2022-04-19
OpenJDK Runtime Environment 18.9 (build 11.0.15+10)
OpenJDK 64-Bit Server VM 18.9 (build 11.0.15+10, mixed mode, sharing)
```

# Install Maven on RHEL or Fedora

```shell
sudo dnf install maven -y
```
Verify the installation

```
mvn --version
```

You'll get output similar to the following:

```text
Apache Maven 3.6.3 (Red Hat 3.6.3-13)
Maven home: /usr/share/maven
Java version: 11.0.15, vendor: Red Hat, Inc., runtime: /usr/lib/jvm/java-11-openjdk-11.0.15.0.10-1.fc35.x86_64
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "5.14.10-300.fc35.x86_64", arch: "amd64", family: "unix"
```

# Install Podman on RHEL 8 or Fedora

```shell
sudo dnf -y update
```

```shell
sudo dnf install -y podman
```
Verify the installation:

```shell
podman version
```
You'll see output similar to the following:

```shell
Version:      3.4.2
API Version:  3.4.2
Go Version:   go1.16.6
Built:        Thu Jan  1 00:00:00 1970
OS/Arch:      linux/amd64
```

# Install Kafka as a container

```shell
podman run -it --name kafka-zkless -p 9092:9092 -e LOG_DIR=/tmp/logs quay.io/strimzi/kafka:latest-kafka-2.8.1-amd64 /bin/sh -c 'export CLUSTER_ID=$(bin/kafka-storage.sh random-uuid) && bin/kafka-storage.sh format -t $CLUSTER_ID -c config/kraft/server.properties && bin/kafka-server-start.sh config/kraft/server.properties'
```

# Running the unit tests

Navigate to the working directory where you installed the the project source code from GitHub:

`/path/to/kafka-java-demo`

Run the following command from the top level of the directory in which you installed the demonstration code. The code runs the unit tests associated with the project. You'll see a steady stream of output produced by the various tests indicating that the Kafka cluster is up and running.

```shell
mvn test
```

The following is a sample of output emitted by the test.

```
2022-05-31 09:50:11 INFO  KafkaMessageHandlerImpl:34 - {"bootstrapServers":"localhost:9092","topic":"mycooltopic","source":"com.demo.kafka.KafkaMessageHandlerImpl","message":"3mWbku7zXv","key":"fbdd1525-cab2-47fa-b553-8adf62adae19"}
2022-05-31 09:50:11 INFO  KafkaMessageHandlerImpl:38 - {"message":"The number of calls is: 1"}
2022-05-31 09:50:11 INFO  KafkaMessageHandlerImpl:34 - {"bootstrapServers":"localhost:9092","topic":"mycooltopic","source":"com.demo.kafka.KafkaMessageHandlerImpl","message":"m5e5Q9kTmL","key":"21d16306-dbf0-446e-b612-dd8d8c1c2d22"}
2022-05-31 09:50:11 INFO  KafkaMessageHandlerImpl:38 - {"message":"The number of calls is: 2"}
2022-05-31 09:50:11 INFO  KafkaMessageHandlerImpl:34 - {"bootstrapServers":"localhost:9092","topic":"mycooltopic","source":"com.demo.kafka.KafkaMessageHandlerImpl","message":"exDFhoX2t3","key":"d93595d9-3759-4c33-bba8-3fba56779e8e"}
2022-05-31 09:50:11 INFO  KafkaMessageHandlerImpl:38 - {"message":"The number of calls is: 3"}
2022-05-31 09:50:11 INFO  KafkaMessageHandlerImpl:34 - {"bootstrapServers":"localhost:9092","topic":"mycooltopic","source":"com.demo.kafka.KafkaMessageHandlerImpl","message":"qmRfhviJ3c","key":"ae89dd45-45ff-4417-a146-9bfe03ecbb4e"}
2022-05-31 09:50:11 INFO  KafkaMessageHandlerImpl:38 - {"message":"The number of calls is: 4"}

```

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

# Open up the Kafka port using the `ufw` utility

Execute these commands if you want to access the Kafka cluster externally from a the remote machine using the IP address of the machine on which you are running Kakfa.

Run ...

`sudo dnf install ufw`

Then ...

`sudo ufw enable && sudo ufw allow 9092`

