FROM centos:7
RUN yum -y update && yum -y install java-11-openjdk && yum -y install net-tools && yum -y install telnet && yum clean all
VOLUME /home/mshmsh0814/storage
ARG JAR_FILE=messenger*.jar
COPY ${JAR_FILE} messenger.jar
ENTRYPOINT ["java","-jar","messenger.jar"]
