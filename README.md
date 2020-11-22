## iboot

#### 系统简介（Description）

> **iboot** 是笔者整理的一个基于 **Spring-Boot** 的前后端分离的 Web 系统,基于 **RBAC** 思想来管理权限，同时还实现了 **动态路由**。Web 后端以 Spring-Boot 为基础，整合了 Spring Security、Spring Actuator、Spring AOP、Spring Quartz、MyBatis、Druid、PageHelper、Swagger 2.0、Knife4j、POI、FastJson等技术框架，而 Web 前端则以 **Vue.js** 为基础，整合了 Element UI、Axios、Vuex、Vue Router、Moment等技术框架。

> **iboot** is a web application based on **Spring-Boot**, whose authority management is realized by thinking of **RBAC**. Besides, **Dynamic Routing** is achieved in this application. In the back end of this application, Spring Security、Spring Actuator、Spring AOP、Spring Quartz、Mybatis、Druid、PageHelper、Swagger 2.0、Knife4j、POI and FastJson are Integrated based on **Spring-Boot**, and the front end of this application integrates Element UI、Axios、Vuex、Vue Router and Moment on the basis of **Vue.js**.

***

#### 系统功能（Functions）

> * 用户管理：对系统用户的基本信息实现管理，例如新增用户、修改用户、配置用户角色、删除用户等。
> * 部门管理：对系统使用者所属部门的基本信息进行管理。
> * 权限管理：即菜单/按钮管理，实现了动态路由、按钮权限等。
> * 角色管理：对系统用户所属的角色信息进行基本管理。
> * MySQL监控：引入了 **Druid Monitor** 实现对 **MySQL** 的良好监控功能。
> * Redis监控：**Redis** 监控主要是监控系统所使用的缓存，包括一些基本信息等等。
> * Swagger文档：**Swagger** 文档以可视化的方式将后端的URL暴露出来，便于前端开发工作。
> * Knife4j文档：**Swagger** 文档的升级版，适当的美化了 **Swagger** 的界面。
> * Actuator监控：引入了 **Spring-boot-admin-starter-server**，以可视化的方式对系统的健康状态进行监控。
> * 系统备份：手动备份数据库表，亦可下载备份文件或者手动还原。
> * 操作日志：记录系统中对数据修改敏感的操作基本信息。
> * 附件管理：管理系统附件文件夹下的所有附件。
> * 定时任务：可以让预先定义的任务以一定的周期执行。
> * 邮件管理：暂未实现。
> * Echarts图表库：**Echarts** 图表库的示例。
> * Element图标库：展示 **Element** 的所有图标样式，并且点击图标即可复制图标对应的icon名。

> * Management of users: Management of basic information of system user, for instance, like add、modify、config、delete and so on.
> * Management of departments: Management of basic information of department system users belong to.
> * Management of menus: Management of menus and buttons, realizing dynamic routing and button authorities.
> * Management of roles: Basic management of information of roles system users belong to.
> * Monitor of MySQL: Import the **Druid Monitor** from **alibaba** to monitor the **MySQL**.
> * Monitor of Redis: Monitor **Redis** server the application connects, including some basic information.
> * Swagger document: The **Swagger** document exposes the URLs embedded in bach end by providing visualization UI, making it available for development of front end.
> * Knife4j document: Update of **Swagger document**, beautify the UI of **Swagger**.
> * Actuator Monitor: Import **Spring-boot-admin-starter-server**, provides visualization UI to monitor the status of application.
> * Backup: Backup the database manually, download the backup files and roll back the database manually.
> * System Log: Record basic information of operations which modify the data.
> * Management of attachment: Management of attachment belonging to the attachment folder.
> * Jobs: Execute a pre-defined task in a certain period.
> * Management of Email: Forget it.
> * Echarts: Display some instances of **Echarts**.
> * Element icons: Display all element icons from **Element UI**, and copy the icon by clicking the icon displayed.

***

#### 使用说明（Instructions）

> 1. 克隆或者下载 **iboot**、**iboot-web**、**iboot-admin** 和 **iboot-attachment** 这四个项目。
> 2. 将 **iboot-attachment/BackUp** 目录下的SQL文件导入数据库中。
> 3. 将 **iboot-web** 导入IDE中，并依次执行 `npm install`、`npm run serve` 来运行前端项目。
> 4. 将 **iboot-admin** 导入IDE中并运行。（非必须，这个是Actuator监控的服务器，提供了可视化的方式对注册到服务器的应用进行监控）
> 5. 将 **iboot** 导入IDE中并运行 **BackApplication.java**。

> 1. Clone or download **iboot**、**iboot-web**、**iboot-admin** and **iboot-attachment**.
> 2. Import the .sql file in **iboot-attachment/BackUp**.
> 3. Import **iboot-web** into your IDE, and run command `npm install` and `npm run serve` to start the front end application of **iboot** successively.
> 4. Import **iboot-admin** into your IDE and just run it. (Not necessary. This application is the server of Actuator Monitor, provides visualization UI to manage applications who has been registered to the server.)
> 5. Import **iboot** into your IDE and just run **BackApplication.java**.

***

####  项目组成（Modules）

|                      | Gitee                                                        | Github                                                       |
| -------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 后端（back end）     | [gitee/iboot](https://gitee.com/lemonpy/iboot)               | [github/iboot](https://github.com/Zon-g/iboot)               |
| 前端（front end）    | [gitee/iboot-web](https://gitee.com/lemonpy/iboot-web)       | [github/iboot-web](https://github.com/Zon-g/iboot-web)       |
| 附件（attachment）   | [gitee/iboot-attachment](https://gitee.com/lemonpy/iboot-attachment) | [github/iboot-attachment](https://github.com/Zon-g/iboot-attachment) |
| admin-UI（admin-UI） | [gitee/iboot-admin](https://gitee.com/lemonpy/iboot-admin)   | [github/iboot-admin](https://github.com/Zon-g/iboot-admin)   |

***

####  运行环境（Enviroment）

| 环境（Environment） | 版本（version） |
| ------------------- | --------------- |
| Java                | 1.8.0           |
| MySQL               | 8.0.21          |
| Redis               | 3.2             |
| @vue/cli            | 4.5.6           |

***

#### 系统界面（The UI）

| ![](https://gitee.com/lemonpy/iboot/raw/master/pics/001.png) | ![](https://gitee.com/lemonpy/iboot/raw/master/pics/002.png) |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![](https://gitee.com/lemonpy/iboot/raw/master/pics/003.png) | ![](https://gitee.com/lemonpy/iboot/raw/master/pics/004.png) |
| ![](https://gitee.com/lemonpy/iboot/raw/master/pics/005.png) | ![](https://gitee.com/lemonpy/iboot/raw/master/pics/006.png) |
| ![](https://gitee.com/lemonpy/iboot/raw/master/pics/007.png) | ![](https://gitee.com/lemonpy/iboot/raw/master/pics/008.png) |
| ![](https://gitee.com/lemonpy/iboot/raw/master/pics/009.png) | ![](https://gitee.com/lemonpy/iboot/raw/master/pics/010.png) |
| ![](https://gitee.com/lemonpy/iboot/raw/master/pics/011.png) | ![](https://gitee.com/lemonpy/iboot/raw/master/pics/012.png) |
| ![](https://gitee.com/lemonpy/iboot/raw/master/pics/013.png) | ![](https://gitee.com/lemonpy/iboot/raw/master/pics/014.png) |
| ![](https://gitee.com/lemonpy/iboot/raw/master/pics/015.png) | ![](https://gitee.com/lemonpy/iboot/raw/master/pics/016.png) |

***

####  参与贡献（Contribution）

> 1. Fork 本仓库。
> 2. 新建 Feat_xxx 分支。
> 3. 提交代码。
> 4. 新建 Pull Request。

> 1. Fork the repository.
> 2. Create Feat_xxx branch.
> 3. Commit your code.
> 4. Create Pull Request.

***

####  致谢（Thanks）

> 感谢 **Jetbrains** 提供的学生版全家桶。

> I appreciate that **Jetbrains** provides product pack for student.

***