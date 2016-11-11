1.0.6-SNAPSHOT
	1.menu菜单 url count属性添加${}配置,由properties文件中的内容替换
	
1.0.7-SNAPSHOT # 由于兼容问题已删除
	1.IBaseDao 增加了两个方法count, list
	2.IbaseService 增加了两个方法count, list 并在AbstractBaseService中默认实现
	
1.0.9-SNAPSHOT
	1.增加了线程池
	
1.0.10-SNAPSHOT (2015-04-12)
	1.在菜单service中增加了删除指定菜单的功能。
	
1.1.0-SNAPSHOT (2015-05-18)
	1.Mybatis物理分页的重写, 改动如下, 详见代码测试TestPage
		1)解决了信息总数缓存与数据缓存不同步的问题
		2)解决了原来多个拦截器之间重复判断问题，新的拦截更加高效及简洁
		3)增加了对注解SQL分页的支持
		4)代码及XML配置更加清晰简洁并优化了缓存, 速度更高效
	2.删除了IPropertyService已经过时的类
	3.菜单配置全部移到/menu文件下面, /core文件的菜单相关文件不再支持
	4.注释的规范化，相关的接口都加了相关的注释
	5.删除的mybatis.xml文件，有点多余了
	6.application.properties 改为webapp.properties 并移至config目录下面，与user.properties,jdbc.properties放在一起
	    修改的目的是命名的简洁化，properties文件的统一路径
	9.spring与Ehcache整合，同时支持手动及注解两种方式的调用，详情见测试类TestCache
	10.体系架构内缓存分为三大块1.mybatis（ehcache.xml） 2.shiro(ehcache-shiro.xml) 3.与spring整合的(ehcache-build.xml) 分配对应着三个配置文件
	    不同的存储目录保存各个缓存的独立，相互之间不会影响
	11.优化propertity的配置
	12.c3p0的配置增加了几项配置
	13.c3p0的配置添加了默认值，没有配置则使用默认值
	14.分页组件添加view类型,AJAX
	
	
