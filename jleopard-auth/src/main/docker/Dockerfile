#  基础镜像
FROM java:8
# 维护者信息
MAINTAINER jleopard@126.com
#Default to UTF-8 file.encoding
ENV LANG C.UTF-8
#设置alpine时区
ENV TIMEZONE Asia/Shanghai
#添加应用
ADD jleopard-auth.jar jleopard-auth.jar
#执行操作 默认启动线上环境
ENTRYPOINT [ "sh", "-c", "java -jar jleopard-auth.jar" ]

