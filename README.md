# iboot

#### 介绍（Description）
> iboot是一个 web 后端架构框架，主要整合了 Spring Boot、Spring Security、Spring Boot Actuator、JWT、Mybatis、PageHelper、Druid、Lombok、Swagger 2.0、Knife4j以及POI等框架，实现了基本的 web 权限管理功能。框架的权限设计基于 **RBAC** 思想，即角色与权限对应、用户与角色对应；角色可拥有多个权限、用户可拥有多种角色。

> iboot is a web back end framework, which integrates Spring Boot、Spring Security、Spring Boot Actuator、JWT、Mybatis、PageHelper、Druid、Lombok、Swagger 2.0、Knife4j and POI to realize the basic management function  of web authority. This framework is designed with thinking **RBAC** , which is **role-based access control**. Roles corresponds to authorities; Users corresponds to roles; A role corresponds to plenty of authorities; A user corresponds to many roles. 

***

#### 项目组成（Items）

|                      | Gitee                                                        | Github                                                       |
| -------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 后端（back end）     | [gitee/iboot](https://gitee.com/lemonpy/iboot)               | [github/iboot](https://github.com/Zon-g/iboot)               |
| 前端（front end）    | [gitee/iboot-web](https://gitee.com/lemonpy/iboot-web)       | [github/iboot-web](https://github.com/Zon-g/iboot-web)       |
| 附件（attachment）   | [gitee/iboot-attachment](https://gitee.com/lemonpy/iboot-attachment) | [github/iboot-attachment](https://github.com/Zon-g/iboot-attachment) |
| admin-UI（admin-UI） | [gitee/iboot-admin](https://gitee.com/lemonpy/iboot-admin)   | [github/iboot-admin](https://github.com/Zon-g/iboot-admin)   |

***

#### 运行环境（Enviroment）

| 环境（Environment） | 版本（version） |
| :-----------------: | :-------------: |
|        Java         |      1.8.0      |
|        MySQL        |     8.0.21      |
|        Redis        |       3.2       |

***

#### 软件架构（Software Architecture）

> * src
>   * main
>     * java
>       * common	----	常用工具，比如附件操作相关、JWT 与 Spring Security 配置、Swagger 配置、返回状态码以及一些简单的工具类。
>       * controller	----	控制器层。
>       * entity	----	实体类层。
>       * logger	----	操作日志，通过注解自动记录用户对数据的修改记录。
>       * mapper	----	数据库访问对象。
>       * service	----	服务层。
>       * BackApplication.java
>     * resource
>       * mapper	----	数据库访问对象 SQL 映射语句。
>       * application.yml	----	系统配置文件
>   * test
> * README.en.md
> * README.md
> * pom.xml

> * src
>   * main
>     * java
>       * common	----	common tools, such as attachment helper class, configuration for JWT, Spring Security and Swagger, response code and some simple tools。
>       * controller	----	controller layer.
>       * entity	----	entity layer.
>       * logger	----	operation logger, records the log when a user modify the data by using self define annotation.
>       * mapper	----	data access object.
>       * service	----	service layer.
>       * BackApplication.java
>     * resource
>       * mapper	----	mapper file for data access object.
>       * application.yml	----	configuration for iboot.
>   * test
> * README.en.md
> * README.md
> * pom.xml

***

#### 使用说明（Instructions）

> 1. 克隆项目。
> 2. 项目导入至IDE。
> 3. 导入项目pom.xml中的依赖。
> 4. 将 **iboot-attachment/BackUp** 目录下的SQL文件导入数据库。
> 5. 运行 **iboot-admin** 项目（非必须）。
> 6. 运行BackApplication.java。

> 1. git clone the repository.
> 2. import the repository into IDE.
> 3. import dependencies in pom.xml.
> 4. import the sql script in the folder **iboot-attachment/BackUp**.
> 5. run application **iboot-admin** (Not necessary).
> 6. run BackApplication.java.

***

#### 系统界面（The Iboot）

| ![](https://gitee.com/lemonpy/iboot/raw/master/pics/001.png) | ![](https://gitee.com/lemonpy/iboot/raw/master/pics/002.png) |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![](https://gitee.com/lemonpy/iboot/raw/master/pics/003.png) | ![](https://gitee.com/lemonpy/iboot/raw/master/pics/004.png) |
| ![](https://gitee.com/lemonpy/iboot/raw/master/pics/005.png) | ![](https://gitee.com/lemonpy/iboot/raw/master/pics/006.png) |
| ![](https://gitee.com/lemonpy/iboot/raw/master/pics/007.png) | ![](https://gitee.com/lemonpy/iboot/raw/master/pics/008.png) |
| ![](https://gitee.com/lemonpy/iboot/raw/master/pics/009.png) | ![](https://gitee.com/lemonpy/iboot/raw/master/pics/010.png) |

***

#### 参与贡献（Contribution）

> 1.  Fork 本仓库。
> 2.  新建 Feat_xxx 分支。
> 3.  提交代码。
> 4.  新建 Pull Request。

> 1.  Fork the repository.
> 2.  Create Feat_xxx branch.
> 3.  Commit your code.
> 4.  Create Pull Request.

***

#### 致谢（Thanks）

> 感谢 Jetbrains 提供的学生版全家桶。

> Here, I appreciate that Jetbrains provides product pack for student.

***