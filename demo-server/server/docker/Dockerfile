# base image
FROM registry.cn-hangzhou.aliyuncs.com/demo/java:openjdk-8-chinese

# MAINTAINER
MAINTAINER yuanshuai@demo.com.cn

RUN mkdir -p /var/data
RUN mkdir -p /var/log/writing2
# media file path
VOLUME /var/data
# log file path
VOLUME /var/log/writing2
# add file
ADD run.sh /run.sh
RUN chmod +x /*.sh
ADD ./writing2.jar /writing2.jar
EXPOSE 8080 5005
CMD ["/run.sh"]
