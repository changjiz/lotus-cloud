
# 约定

1. 配置文件所有以下划线 `_` 分割的数据源 **首部** 即为组的名称，相同组名称的数据源会放在一个组下。
2. 切换数据源可以是组名，也可以是具体数据源名称。组名则切换时采用负载均衡算法切换。
3. 默认的数据源名称为  **master** ，你可以通过 `spring.datasource.dynamic.primary` 修改。
4. 方法上的注解优先于类上注解。
5. DataSource支持继承抽象类上的DataSource，暂不支持继承接口上的DataSource。

# 使用方法

```
2. 配置数据源。

```yaml
spring:
  datasource:
    dynamic:
      enabled: true
      primary: master #设置默认的数据源或者数据源组,默认值即为master
      driver-class-name: com.mysql.cj.jdbc.Driver
      datasource:
        master:
          pool-name: master-pool
          url: jdbc:mysql://xx.xx.xx.xx:3306/dynamic
          username: root
          password: 123456
        slave_1:
          url: jdbc:mysql://xx.xx.xx.xx:3307/dynamic
          username: root
          password: 123456
        slave_2:
          url: jdbc:mysql://xx.xx.xx.xx:3307/dynamic
          username: root
          password: 123456
       #......省略
       #以上会配置一个默认库master，一个组slave下有两个子库slave_1,slave_2
```

```yaml
# 多主多从                     
spring:
  datasource:        
    dynamic:                        
      datasource:                   
        master_1:                   
        master_2:                            
        slave_1:                           
        slave_2:    
        slave_3:    
```

3. 使用  **@DataSource**  切换数据源。

**@DataSource** 可以注解在方法上或类上，**同时存在就近原则 方法上注解 优先于 类上注解**。

|     注解      |                   结果                   |
| :-----------: | :--------------------------------------: |
|    没有@DataSource    |                默认数据源                |
| @DataSource("dsName") | dsName可以为组名也可以为具体某个库的名称 |

```java
@Service
@DataSource("slave")
public class UserServiceImpl implements UserService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List selectAll() {
    return  jdbcTemplate.queryForList("select * from user");
  }
  
  @Override
  @DataSource("slave_1")
  public List selectByCondition() {
    return  jdbcTemplate.queryForList("select * from user where age >10");
  }
}
```


# 注意点
1. 在同一事务下是无法切换数据源的，一个事务只获取一次数据源。
2. 需关注TxAdviceAspect事务切面点。
3. 如果需要切换事务来达到切换数据源，使用@Transactional手动设置事务传播特性。