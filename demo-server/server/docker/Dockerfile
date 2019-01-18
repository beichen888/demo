# base image
FROM registry.cn-hangzhou.aliyuncs.com/demo/java:openjdk-8-chinese

# MAINTAINER
MAINTAINER yuanshuai@demo.com.cn

RUN mkdir -p /var/data
RUN mkdir -p /var/log/demo
# media file path
VOLUME /var/data
# log file path
VOLUME /var/log/demo
# add file
ADD run.sh /run.sh
RUN chmod +x /*.sh
ADD ./demo.jar /demo.jar
EXPOSE 8080 5005
CMD ["/run.sh"]
