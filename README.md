### 一、开发环境及工具

---

#### Windows10

- IDEA2019
- Navicat
- tomcat

### 二、项目描述

---

![UML2](C:\Users\12157\Desktop\UML2.png)



### 三、功能需求

---

#### 后台原型（src/main/java）

> **java-Controller**
>
> > `com.webturing.noshop.controller`的`list(Model model)`方法中，通过`categoryService.list()`获取所有`Category`对象，放在`cs`，并服务端跳转`admin/listCategory.jsp`文件。
>



#### webapp

>
>> `web.xml`:
>>
>> + 指定spring配置文件为classpath下的applicationContext.xml
>>
>> ```xml
>> <context-param>
>> <param-name>contextConfigLocation</param-name>
>> <param-value>classpath:applicationContext.xml</param-value>
>> </context-param>
>> ```
>>
>> + 指定springMVC配置文件
>> + 中文过滤
>>
>> 
>>
>> ### 静态资源
>>
>> #### /CSS
>>
>> > - /back
>> >
>> >   含有style.css为后台前端页面样式
>> >
>> > - /bootstrap
>> >
>> >   bootstrap的css样式文件
>>
>> 
>>
>> #### /JS
>>
>> > + /bootstrap
>> >
>> >     bootstrap的js文件
>> >
>> > + /JQuery
>> >
>> >     JQuery的js文件
>>
>> 
>>
>> #### /admin 重定向导航页
>>
>> 
>>
>> #### /img 图片资源
>>
>> 
>>
>> #### /jsp/admin/listCategory.jsp
>>
>> + **引入bootstrap、后台样式style.css、jquery**
>>
>> ```jsp
>> <script src="js/jquery/2.0.0/jquery.min.js"></script>
>> <link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
>> <script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
>> <link href="css/back/style.css" rel="stylesheet">
>> ```
>> + 遍历cs对象内容并显示
>>
>> ```jsp
>> <c:forEach items="${cs}" var="c">
>> 
>>     <tr>
>>         <td>${c.id}</td>
>>         <td><img height="40px" src="img/category/${c.id}.jpg"></td>
>>         <td>${c.name}</td>
>> 
>>         <td><a href="admin_property_list?cid=${c.id}"><span class="glyphicon glyphicon-th-list"></span></a></td>
>>         <td><a href="admin_product_list?cid=${c.id}"><span class="glyphicon glyphicon-shopping-cart"></span></a></td>
>>         <td><a href="admin_category_edit?id=${c.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
>>         <td><a deleteLink="true" href="admin_category_delete?id=${c.id}"><span class="   glyphicon glyphicon-trash"></span></a></td>
>> 
>>     </tr>
>> </c:forEach>
>> ```
>> + 123
>> + 123

#### Resources

> > ##### /mapper中`CategoryMapper.xml`声明sql语句用来逆序查询category对象
> >
> > ```xml
> > <mapper namespace="com.webturing.noshop.mapper.CategoryMapper">
> > <select id="list" resultType="Category">
> > select * from   category order by id desc
> > </select>
> > </mapper>
> > ```
> >
> > 
> >
> > `log4j.properties`开启日志(查看mybatis运行，以及sql信息)
> >
> > 
> >
> > `jdbc.propertiex`配置数据库
> >
> > 
> >
> > `applicationContext.xml`: 
> >
> > - 配置相应数据源```<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">```
> > - 配置Mybatis的SessionFactory```<bean id="sqlSession" class="org.mybatis.spring.SqlSessionFactoryBean">```
> > - Mapper类扫描
> >
> > ```xml
> > <bean id="sqlSession" class="org.mybatis.spring.SqlSessionFactoryBean">
> > ```
> >
> > + Mybatis的Mapper文件识别
> >
> > ```xml
> > <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
> >     <property name="basePackage" value="com.webturing.noshop.mapper"/>
> > </bean>
> > ```
> >
> > `springMVC.xml`:
> >
> > - 开启静态资源访问，防止访问时出错`<mvc:default-servlet-handler/>`
> > - 视图定位到/WEB-INF/JSP/*.jsp
> >
> > ```xml
> > <bean
> > class="org.springframework.web.servlet.view.InternalResourceViewResolver">
> > <property name="viewClass"
> >        value="org.springframework.web.servlet.view.JstlView" />
> >     <property name="prefix" value="/WEB-INF/jsp/" />
> >     <property name="suffix" value=".jsp" />
> > </bean>
> > ```
> >
> > - 解析上传的文件
> >
> > ```xml
> > <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"/>
> > ```
> >
> > 
>