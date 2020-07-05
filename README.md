### 一、开发环境及工具

---

#### Windows10

- IDEA2019
- Navicat
- tomcat

---

### 二、项目描述

---

#### Tmall后台简化版本

##### 第一阶段功能：显示出后台页面![UML2](C:\Users\12157\Desktop\UML2.png)

##### 第二阶段功能：

- **添加分页功能**

- **添加删除功能**
- **添加管理功能**

---



### 三、功能需求实现

---

#### JAVA

> #### pojo
>
> > Category.java实体类声明id、name，对于setter、getter
>
> 
>
> #### mapper
>
> > CategoryMapper.java接口，查询使用
>
> 
>
> #### service
>
> > CategoryService.java接口，查询使用
> >
> > CategoryServiceImpl.java实现类
>
> 
>
> #### controller
>
> > CategoryController.java类
>
> + 由CategoryController处理/admin_category_list页面从而跳转listCategory.jsp文件
>
> ```java
> @RequestMapping("admin_category_list")
> public String list(Model model, Page page){
>     ...
>     return "admin/listCategory";
> }
> ```
>
> 
>
> #### util
>
> > Page.java分页(基于查询)
> + 相应参数
>
> ```java
> private int start; //开始数据的索引
> private int count; //每页最多可显示的个数
> private int total; //总个数
> private String param; //参数
> private static final int defaultCount = 5; //默认每页显示5条
> ```
>
> + 根据每页显示count以及数据条数total计算总页数
>
> ```java
> public int getTotalPage(){
>  int totalPage;
>  // 假设总数是50，是能够被5整除的，那么就有10页
>  if (0 == total % count)
>  totalPage = total /count;
>  // 假设总数是51，不能够被5整除的，那么就有11页
>  else
>  totalPage = total / count + 1;
> 
>  if(0==totalPage)
>  totalPage = 1;
>  return totalPage;
> }
> ```
>   + 计算最后一页数据条数
>
> ```java
> public int getLast(){
>  int last;
>  // 假设总数是50，是能够被5整除的，那么最后一页的开始就是45
>  if (0 == total % count)
>  	last = total - count;
>  // 假设总数是51，不能够被5整除的，那么最后一页的开始就是50
>   else
>  	last = total - total % count;
>  last = last<0?0:last;
>   return last;
> }
> ```
> + 判断是否有前一页
>
> ``` java
> public boolean isHasPreviouse(){
>  if(start==0)
>   return false;
>  return true;
> }
> ```
>
> + 判断是否有后一页
>
> ```java
> public boolean isHasNext(){
>  if(start==getLast())
>   return false;
>  return true;
> }
> ```
> + 在listCategory.jsp中用到的param参数
> ```java
> public String getParam() {
>   return param;
> }
> public void setParam(String param) {
>   this.param = param;
> }
> ```
> 



#### webapp

>#### admin
>
>> 重定向
>
>
>
>#### css
>
>> /back
>>
>> + `style.css`页面样式
>>
>> /bootstrap
>>
>> + 导入的bootstrap的css资源
>
>
>
>#### js
>
>> /bootstrap
>>
>> + 导入的bootstrap的js资源
>>
>> /jqury
>>
>> + 导入jquery资源
>
>
>
>#### /WEB-INF
>
>> ##### admin
>
>​	`listCategory.js`
>
>+ 引入bootstrap、后台样式style.css、jquery
>
>```jsp
><script src="js/jquery/2.0.0/jquery.min.js"></script>
><link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
><script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
><link href="css/back/style.css" rel="stylesheet">
>```
>
>+ 遍历CategoryController中的对象并把内容显示
>
>```jsp
><c:forEach items="${cs}" var="c">
>
><tr>
><td>${c.id}</td>
><td><img height="40px" src="img/category/${c.id}.jpg"></td>
><td>${c.name}</td>
>
><td><a href="admin_property_list?cid=${c.id}"><span class="glyphicon glyphicon-th-list"></span></a></td>
><td><a href="admin_product_list?cid=${c.id}"><span class="glyphicon glyphicon-shopping-cart"></span></a></td>
><td><a href="admin_category_edit?id=${c.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
><td><a deleteLink="true" href="admin_category_delete?id=${c.id}"><span class="   glyphicon glyphicon-trash"></span></a></td>
>
></tr>
></c:forEach>
>```
>
>
>
>> ##### include/admin
>
>​	`adminPage.jsp`
>
>+ 点击脚本
>
>```jsp
><script>
>$(function(){
>	$("ul.pagination li.disabled a").click(function(){
>		return false;
>	});
>});
></script>
>```
>
>+ 分页显示界面
>
>通过CategoryController向前台admin.jsp传递的page对象控制分页，用bootstrap分页效果制作,同时使用CategoryMapper.xml的分页语句`limit #{start},#{count}`获取参数
>
>1.首页
>
>```jsp
><li>
><a  href="?start=0" aria-label="Previous" >
><span aria-hidden="true">«</span>
></a>
></li>
>```
>2.上一页
>
>```jsp
><li >
><a  href="?start=${page.start-page.count}" aria-label="Previous" >
><span aria-hidden="true">‹</span>
></a>
></li>
>```
>
>3.中间页
>
>​	`status.index`当前页面下标，与count相乘得到整数即得到start的值
>
>```jsp
><c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
><li>
><a href="?start=${status.index*page.count}" class="current">${status.count}</a>
></li>
></c:forEach>
>```
>
>4.下一页
>
>```jsp
><li >
><a href="?start=${page.start+page.count}" aria-label="Next">
><span aria-hidden="true">›</span>
></a>
></li>
>```
>
>5.尾页
>
>```jsp
><li >
><a href="?start=${page.last}" aria-label="Next">
><span aria-hidden="true">»</span>
></a>
></li>
>```
>
>
>
>##### /web.xml
>
>1.指定spring的配置文件为classpath下的applicationContext.xml
>
>```xml
><context-param>
>    <param-name>contextConfigLocation</param-name>
>    <param-value>classpath:applicationContext.xml</param-value>
></context-param>
><listener>
>    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
></listener>
>```
>
>
>
>2.中文过滤器
>
>```xml
><filter>
>    <filter-name>CharacterEncodingFilter</filter-name>
>    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
>    <init-param>
>        <param-name>encoding</param-name>
>        <param-value>utf-8</param-value>
>    </init-param>
></filter>
><filter-mapping>
>    <filter-name>CharacterEncodingFilter</filter-name>
>    <url-pattern>/*</url-pattern>
></filter-mapping>
>```
>
>
>
>3.指定spring mvc配置文件为classpath下的springMVC.xml
>
>```xml
><!-- spring mvc核心：分发servlet -->
><servlet>
>    <servlet-name>mvc-dispatcher</servlet-name>
>    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
>    <!-- spring mvc的配置文件 -->
>    <init-param>
>        <param-name>contextConfigLocation</param-name>
>        <param-value>classpath:springMVC.xml</param-value>
>    </init-param>
>    <load-on-startup>1</load-on-startup>
></servlet>
><servlet-mapping>
>    <servlet-name>mvc-dispatcher</servlet-name>
>    <url-pattern>/</url-pattern>
></servlet-mapping>
>```
>
>

#### Resources

> #####/mapper
>
> > `CategoryMapper.xml`
> >
> > + 声明sql语句用来逆序查询category对象
> >
> > ```xml
> > <select id="list" resultType="Category">
> > 	select * from   category order by id desc
> > </select>
> > ```
> >
> > + 分页查询
> >
> > ```xml
> > <!-- 分页查询 -->
> > <if test="start!=null and count!=null">
> > 	limit #{start},#{count}
> > </if>
> > ```
> >
> > + 获取数据条数
> >
> > ```xml
> > <select id="total" resultType="int">
> > 	select count(*) from category
> > </select>
> > ```
> >
>
> 
>
> > `log4j.properties`开启日志(查看mybatis运行，以及sql信息)
> >
>
> 
>
> > `jdbc.propertiex`配置数据库
> >
> > ```properties
> >#数据库配置文件
> > jdbc.driver=com.mysql.jdbc.Driver
> > jdbc.url=jdbc:mysql://localhost:3306/tmall_ssm?useUnicode=true&characterEncoding=utf8
> > jdbc.username=root
> > jdbc.password=admin
> > ```
> >
> > 
>
> 
>
> > `applicationContext.xml`: 
> >
> > - 配置相应数据源
> >
> > ```xml
> > <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
> > ```
> > 
> > - 配置Mybatis的SessionFactory
> >
> > ```xml
> ><bean id="sqlSession" class="org.mybatis.spring.SqlSessionFactoryBean">
> > ```
> > 
> > - Mapper类扫描
> > 
> > ```xml
> > <bean id="sqlSession" class="org.mybatis.spring.SqlSessionFactoryBean">
> >```
> > 
> >+ Mybatis的Mapper文件识别
> > 
> > ```xml
> > <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
> > <property name="basePackage" value="com.webturing.noshop.mapper"/>
> > </bean>
> >```
> > 
>
> 
>
> > `springMVC.xml`:
> >
> > - 开启静态资源访问，防止访问时出错`<mvc:default-servlet-handler/>`
> >- 视图定位到/WEB-INF/JSP/*.jsp
> > 
> > ```xml
> > <bean
> > class="org.springframework.web.servlet.view.InternalResourceViewResolver">
> > <property name="viewClass"
> > value="org.springframework.web.servlet.view.JstlView" />
> > <property name="prefix" value="/WEB-INF/jsp/" />
> ><property name="suffix" value=".jsp" />
> > </bean>
> > ```
> > 
> > - 解析上传的文件
> > 
> > ```xml
> ><bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"/>
> > ```
> >
> > 
>