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
> > `Category.java`实体类声明id、name，对于setter、getter
>
> 
>
> #### mapper
>
> > `CategoryMapper.java`接口，查询使用
>
> 
>
> #### service
>
> > `CategoryService.java`接口，查询使用
> >
> > `CategoryServiceImpl.java`实现类
>
> 
>
> #### controller
>
> > `CategoryController.java`类
>
> + 由`CategoryController`处理`/admin_category_list`页面从而跳转`listCategory.jsp`文件
>
> ```java
> @RequestMapping("admin_category_list")
> public String list(Model model, Page page){
> ...
> return "admin/listCategory";
> }
> ```
>
> + add方法的实现
>
> 1. 映射路径`admin_category_add`
>
> 2. 参数
>
>    + `Category c`接受页面提交的分类名称
>
>    + `HttpSession session` 用于获取当前路径
>    + `UploadedImageFile uploadedImageFile`用于接收上传图片
>
> ```java
> public String add(
>  Category c, 
>  HttpSession session, 
>  UploadedImageFile uploadedImageFile) throws IOException {...}
> ```
>
> 3. 通过`session`获取`ServletContext`,再通过getRealPath定位存放分类图片的路径。
>
> ```java
> File  imageFolder= new File(session.getServletContext().getRealPath("img/category"));
> ```
>
> 4. 根据分类`id`创建文件名
>
> ```java
> File file = new File(imageFolder,c.getId()+".jpg");
> ```
>
> 5. 如果`/img/category`目录不存在，则创建该目录
>
> ``` java
> if(!file.getParentFile().exists())
>  file.getParentFile().mkdirs();
> ```
>
> 6. 通过`UploadedImageFile` 把浏览器传递过来的图片保存在上述指定的位置
>
> ```java
> uploadedImageFile.getImage().transferTo(file);
> ```
>
> 7. 通过`ImageUtil.change2jpg(file);` 确保图片格式一定是jpg，而不仅仅是后缀名是jpg
>
> ```java
> BufferedImage img = ImageUtil.change2jpg(file);
> ImageIO.write(img, "jpg", file);
> ```
>
> 8. 跳转`admin_category_list`
>
> ```java
> @RequestMapping("admin_category_add")
> public String add(Category c, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
>  categoryService.add(c);
>  File  imageFolder= new File(session.getServletContext().getRealPath("img/category"));
>  File file = new File(imageFolder,c.getId()+".jpg");
>  if(!file.getParentFile().exists())
>      file.getParentFile().mkdirs();
>  uploadedImageFile.getImage().transferTo(file);
>  BufferedImage img = ImageUtil.change2jpg(file);
>  ImageIO.write(img, "jpg", file);
>  
>  return "redirect:/admin_category_list";
> }
> ```
>
> + 删除方法的实现
>
> 1. 映射`admin_category_delete`
>
> ```java
> @RequestMapping("admin_category_delete")
> ```
>
> 2. 提供`id`注入
>
> ```java
> public String delete(int id,HttpSession session) {
>     categoryService.delete(id);
>     ...
> }
> ```
>
> 3. `session`定位文件位置,`categoryService`删除数据,删除图片
>
> ```java
> File  imageFolder= new File(session.getServletContext().getRealPath("img/category"));
> File file = new File(imageFolder,id+".jpg");
> file.delete();
> 
> return "redirect:/admin_category_list";
> ```
>
> 
>
> #### util
>
> > `Page.java`分页(基于查询)
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
> + 根据每页显示`count`以及数据条数`total`计算总页数
>
> ```java
> public int getTotalPage(){
> int totalPage;
> // 假设总数是50，是能够被5整除的，那么就有10页
> if (0 == total % count)
> totalPage = total /count;
> // 假设总数是51，不能够被5整除的，那么就有11页
> else
> totalPage = total / count + 1;
> 
> if(0==totalPage)
> totalPage = 1;
> return totalPage;
> }
> ```
>   + 计算最后一页数据条数
>
> ```java
> public int getLast(){
> int last;
> // 假设总数是50，是能够被5整除的，那么最后一页的开始就是45
> if (0 == total % count)
> 	last = total - count;
> // 假设总数是51，不能够被5整除的，那么最后一页的开始就是50
> else
> 	last = total - total % count;
> last = last<0?0:last;
> return last;
> }
> ```
> + 判断是否有前一页
>
> ``` java
> public boolean isHasPreviouse(){
> if(start==0)
> return false;
> return true;
> }
> ```
>
> + 判断是否有后一页
>
> ```java
> public boolean isHasNext(){
> if(start==getLast())
> return false;
> return true;
> }
> ```
> + 在`listCategory.jsp`中用到的`param`参数
> ```java
> public String getParam() {
> return param;
> }
> public void setParam(String param) {
> this.param = param;
> }
> ```
>
>
> > `UploadedImageFile.java`
>
> + `MultipartFile` 类型的属性,用于接受上传文件的注入。(已经在`listCategory.js`中创建了上传表单)
>
> ```java
> public class UploadedImageFile {
>  MultipartFile image;
> 
>  public MultipartFile getImage() {
>      return image;
>  }
> 
>  public void setImage(MultipartFile image) {
>      this.image = image;
>  }
>  
> }
> ```
>
> 
>
> > `ImageUtil.java`工具类用来处理图片
>
> ```java
> 直接拿着用
> ```
>
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
>+ 对分类名称和分类图片做了为空判断，当为空的时候，不能提交
>
>```jsp
><script>
>$(function(){
>
>   $("#addForm").submit(function(){
>       if(!checkEmpty("name","分类名称"))
>           return false;
>       if(!checkEmpty("categoryPic","分类图片"))
>           return false;
>       return true;
>   });
>});
>
></script>
>```
>
>+ 删除监听
>
>```jsp
>$(function(){
>    $("a").click(function(){
>        var deleteLink = $(this).attr("deleteLink");
>        console.log(deleteLink);
>        if("true"==deleteLink){
>            var confirmDelete = confirm("确认要删除");
>            if(confirmDelete)
>                return true;
>            return false;
>             
>        }
>    });
>})
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
>  通过`CategoryController`向前台`adminPage.jsp`传递的`page`对象控制分页，用`bootstrap`分页效果制作,同时使用`CategoryMapper.xml`的分页语句`limit #{start},#{count}`获取参数
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
>> ##### /web.xml
>
>1.指定spring的配置文件为classpath下的`applicationContext.xml`
>
>```xml
><context-param>
><param-name>contextConfigLocation</param-name>
><param-value>classpath:applicationContext.xml</param-value>
></context-param>
><listener>
><listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
></listener>
>```
>
>
>
>2.中文过滤器
>
>```xml
><filter>
><filter-name>CharacterEncodingFilter</filter-name>
><filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
><init-param>
>   <param-name>encoding</param-name>
>   <param-value>utf-8</param-value>
></init-param>
></filter>
><filter-mapping>
><filter-name>CharacterEncodingFilter</filter-name>
><url-pattern>/*</url-pattern>
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
><servlet-name>mvc-dispatcher</servlet-name>
><servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
><!-- spring mvc的配置文件 -->
><init-param>
>   <param-name>contextConfigLocation</param-name>
>   <param-value>classpath:springMVC.xml</param-value>
></init-param>
><load-on-startup>1</load-on-startup>
></servlet>
><servlet-mapping>
><servlet-name>mvc-dispatcher</servlet-name>
><url-pattern>/</url-pattern>
></servlet-mapping>
>```
>
>

#### Resources

> ##### /mapper
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
> > + 插入分类
> >
> > ```xml
> > <insert id="add"  keyProperty="id"  useGeneratedKeys="true" parameterType="Category" >
> >  insert into category ( name ) values (#{name})
> > </insert>
> > ```
> >
> > + 删除
> >
> > ```xml
> > <delete id="delete">
> >     delete from category where id= #{id}
> > </delete>
> > ```
> >
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