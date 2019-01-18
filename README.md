## XXAPI部署文档

#### 依赖的服务

- MySQL
- JDK8

#### 配置文件

- 数据配置 `src/main/resources/application.yml,application-prod.yml`
  - spring.datasource.driver-class-name:数据库驱动
  - spring.datasource.type:连接池（目前用druid连接池）
  - spring.datasource.url: 数据库访问地址
  - spring.datasource.username: 用户名
  - spring.datasource.password: 密码

- 服务配置 `src/main/resources/application.yml,application-prod.yml,application-config.yml`
  - API开放端口:server - port
  - 上传目录: upload - folder
  - 域名: web - domain
  - 邮箱：mail
  - bms服务：projet - bms
  - 微信服务：project - weixin
  - 通用服务： project - commonService
  - 静态文件访问目录: media - domain

  
  - 微信appid: weixin - appid
  - 微信appsecret: weixin - appsecret

#### 数据库初始化(暂无,使用domain注解自动生成)

- SQL文件
  - 创建数据库 `template_db`
  - 导入SQL文件: `db/init.sql`

#### 打包&启动方式
- 开发者
  - 启动服务:`gradle bootRun`
  - 访问地址: 127.0.0.1:8080

- 测试环境
  - 安装依赖并打包:`gradle bootWar`，`build/lib/server.war`文件为打包后的文件
  - 启动服务:`nohup java -Dserver.port=9090 -jar api.war --spring.profiles.active=dev &`
  
- 生产环境
  - 运行`gradle war2` 生成war包并复制到docker目录
  - 使用 `docker/Dockerfile`生成docker image registry.cn-hangzhou.aliyuncs.com/zhiyou/demo-api:[镜像版本号]
  - 将镜像上传到阿里云容器镜像服务
    - `docker login --username=demo registry.cn-hangzhou.aliyuncs.com`
    - `docker push registry.cn-hangzhou.aliyuncs.com/demo/api:[镜像版本号]`
  - 以上步骤可以使用jenkins完成 参照 http://192.168.1.55:8080/job/demo-product/
  
  - 使用portainer发布docker service
    - 登录https://portainer.demo.com.cn
    - 进入service，更新demo-api服务

