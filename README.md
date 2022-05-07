# guli_School
## 项目技术选型

* **前端部分**：使用主流的前端框架Vue，使用Es6的开发规范，采用模块化的开发模式。
* **后端部分**：
  1. 使用目前流行的SpringBoot+SpringCloud进行微服务架构，使用Feign、Gateway、Hystrix，以及阿里巴巴的Nacos等组件搭建了项目的基础环境。
  2. 采用mybatis-plus提高开发效率，使用代码生成器生成主框架，简化开发步骤。
  3. 使用swagger来测试接口
  4. 使用SpringSecurity整合项目登陆和权限管理，Redis进行数据缓存，将常用的数据存入 Redis，减少数据库访问量和并发量。
  5. 使用了阿里云视频点播功能内嵌在网页中可直接观看视频。
