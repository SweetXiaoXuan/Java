# [java处理emoji]

最近对接ios、安卓客户端，需要处理emoji等表情符号，网上总结：

1.过滤掉emoji表情符

2.修改数据库的编码格式等，让其支持存储emoji

以下分别对两种方案进行描述：

第一种：过滤掉emoji表情符，统一用*代替
利用emoji的编码范围，利用正则匹配过滤：source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*")

private static boolean isNotEmojiCharacter(char codePoint) 
{ 
return (codePoint == 0x0) || 
(codePoint == 0x9) || 
(codePoint == 0xA) || 
(codePoint == 0xD) || 
((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || 
((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || 
((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)); 
} 
/** 
\* 过滤emoji 或者 其他非文字类型的字符 
\* @param source 
\* @return 
*/ 
public static String filterEmoji(String source) 
{ 
int len = source.length(); 
StringBuilder buf = new StringBuilder(len); 
for (int i = 0; i < len; i++) 
{ 
char codePoint = source.charAt(i); 
if (isNotEmojiCharacter(codePoint)) 
{ 
buf.append(codePoint); 
} else{

buf.append("*");

}
} 
return buf.toString(); 
}

第二种：修改mysql数据库的配置，存储emoji表情符

如果UTF8字符集且是Java服务器的话，当存储含有emoji表情时，会抛出类似如下异常：

```
java.sql.SQLException: Incorrect string value: '\xF0\x9F\x92\x94' for column 'name' at row 1  
    at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:1073)  
    at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3593)  
    at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3525)  
    at com.mysql.jdbc.MysqlIO.sendCommand(MysqlIO.java:1986)  
    at com.mysql.jdbc.MysqlIO.sqlQueryDirect(MysqlIO.java:2140)  
    at com.mysql.jdbc.ConnectionImpl.execSQL(ConnectionImpl.java:2620)  
    at com.mysql.jdbc.StatementImpl.executeUpdate(StatementImpl.java:1662)  
    at com.mysql.jdbc.StatementImpl.executeUpdate(StatementImpl.java:1581)
```

这就是字符集不支持的异常。因为UTF-8编码有可能是两个、三个、四个字节，其中Emoji表情是4个字节，有的甚至6个字节，而Mysql的utf8编码最多3个字节，所以导致了数据插不进去。

## 升级前需要考虑的问题：

如果你的项目要进行移动产品的用户文本的存储，将你的DB字符集从UTF8/GBK等传统字符集升级到utf8mb4将是势在必行。你可以通过应用层面转换emoji等特殊字符，以达到原DB的兼容，我认为可行，但是你可能走了弯路。

utf8mb4作为utf8的super set，完全向下兼容，所以不用担心字符的兼容性问题。切换中需要顾虑的主要影响是mysql需要重新启动（虽然mysql官方文档说可以动态修改配置，但是经过数次测试，还是需要重启才可生效），对于业务可用率的影响是需要考虑的大问题，这里就暂时不展开讨论了。

## 升级步骤：

1.utf8mb4的最低mysql版本支持版本为5.5.3+，若不是，请升级到较新版本。

mysql版本查看命令请看：[查看mysql版本的四种方法](http://www.cnblogs.com/end/archive/2011/10/18/2216461.html)；mysql安装步骤请看：[Linux中升级Mysql到Mysql最新版本的方法](http://www.111cn.net/sys/linux/61026.htm)
2.修改database、table和column字符集。参考以下语句：
ALTER DATABASE database_name CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
ALTER TABLE table_name CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE table_name CHANGE column_name VARCHAR(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
3.修改mysql配置文件my.cnf（windows为my.ini）

my.cnf一般在etc/mysql/my.cnf位置。找到后请在以下三部分里添加如下内容：

[client]
default-character-set = utf8mb4

[mysql]
default-character-set = utf8mb4

[mysqld]
character-set-client-handshake = FALSE
character-set-server = utf8mb4
collation-server = utf8mb4_unicode_ci
init_connect='SET NAMES utf8mb4'

4.重启 MySQL Server、检查字符集

1.）重启命令参考：/etc/init.d/mysql restart

2.）输入命令：mysql，进入mysql命令行（如果提示没权限，可以尝试输入mysql -uroot -p你的密码）

3.）在mysql命令行中输入：SHOW VARIABLES WHERE Variable_name LIKE 'character_set_%' OR Variable_name LIKE 'collation%';

检查是否如下：

+--------------------------+--------------------+
| Variable_name            | Value              |
+--------------------------+--------------------+
| character_set_client    | utf8mb4            |
| character_set_connection | utf8mb4            |
| character_set_database  | utf8mb4            |
| character_set_filesystem | binary            |
| character_set_results    | utf8mb4            |
| character_set_server    | utf8mb4            |
| character_set_system    | utf8              |
| collation_connection    | utf8mb4_unicode_ci |
| collation_database      | utf8mb4_unicode_ci |
| collation_server        | utf8mb4_unicode_ci |
+--------------------------+--------------------+
rows in set (0.00 sec)

特别说明下：collation_connection/collation_database/collation_server如果是utf8mb4_general_ci，没有关系。但必须保证character_set_client/character_set_connection/character_set_database/character_set_results/character_set_server为utf8mb4。关于这些字符集配置是干什么用的，有什么区别，请参考：[深入Mysql字符集设置](http://www.laruence.com/2008/01/05/12.html)

5.如果你用的是java服务器，升级或确保你的mysql connector版本高于5.1.13，否则仍然无法使用utf8mb4
这是mysql官方[release note](http://dev.mysql.com/doc/relnotes/connector-j/en/news-5-1-13.html)，大家可以查看说明，并下载最新的mysql connector for java的jar包。
这里为大家提供一个：[mysql-connector-java-5.1.31-bin.jar](http://www.ilikewhite.com/wp-content/uploads/2014/07/mysql-connector-java-5.1.31-bin.zip)
同时记得修改pom配置哦~

6.检查服务端的db配置文件：

jdbc.driverClassName=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/database?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE
jdbc.username=root
jdbc.password=password

 

特别说明其中的jdbc.url配置：如果你已经升级好了mysql-connector，其中的characterEncoding=utf8可以被自动被识别为utf8mb4（当然也兼容原来的utf8），而autoReconnect配置我强烈建议配上，我之前就是忽略了这个属性，导致因为缓存缘故，没有读取到DB最新配置，导致一直无法使用utf8mb4字符集，多么痛的领悟！！

感谢这位仁兄的分享，顺利的帮助我解决了问题，但是我直接拿maven下载的驱动5.1.31还是不好用，后来修改成5.1.13后就没有问题了。