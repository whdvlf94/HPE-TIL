# MyBatis



## 1. log4j

> logging을 위한 Open Source Library



- log문의 출력을 다양한 대상으로 할 수 있도록 도와주는 도구(오픈소스)

- 개발자가 직접 로그출력 여부를 런타임에 조정

- **중요) logger는 로그레벨을 가지고 있으며, 로그의 출력 여부는 로그문의 레벨과 logger의 레벨을 가지고 결정**

  ```java
  	@Test
  	public void sql2() {
  		//특정 user를 update
  		UserVO updateUser = new UserVO("java", "자바2", "여2", "제주2");
  		int cnt = sqlSession.update("userNS.updateUser", updateUser);
  		logger.info(">>> update cnt " + cnt);
  		
  		List<UserVO> selectList = sqlSession.selectList("userNS.selectUserList");
  		for (UserVO userVO : selectList) {
  			System.out.println(userVO);
  			logger.debug(userVO);
  		}
  	}
  ```

  **※ logger console 레벨을 `info`로 설정**했기 때문에, **더 낮은 레벨인 `debug`는 콘솔 창에 출력되지 않는다. -> appender(log 어디에 출력할 것 인가) 설정**

  ​	(loggerfile은 debug 레벨로 설정했으므로 debug를 확인할 수 있다.)



## 2. Mapper 인터페이스

> Mapping 파일에 기재된 SQL을 호출하기 위한 인터페이스



- **Mapping 파일에 있는 SQL**을 **자바 인터페이스를 통해 호출**할 수 있도록 해준다.



### 2.1) Mapper 인터페이스를 사용하지 않았을 때

- SqlSession의 메서드의 argument에 문자열로 `Namespace.SQL ID`로 지정해야 함.

- 문자열로 지정하기 때문에 code assist 사용 불가. **즉, 컴파일로는 오류를 발견할 수 없고, 런타임 시에만 확인할 수 있다.**

  ```java
  UserVO user = 
      sqlSession.selectOne("userNS.selectUserById", "gildong");
  ```



### 2.2) Mapper 인터페이스를 사용하였을 때

- **UserMapper 인터페이스는 개발자가 작성**

  ```java
  	@Test
  	public void service() {
  		//UserService -> UserDao -> SqlSession -> SqlSessionFactory -> DataSource
  		
  		UserVO user = userService.getUser("gildong");
  		System.out.println(user);
  	}
  ```



- **Controller** : 화면과 Service를 연결
- **Service** : Business Logic 
  - **@Service**
  - ex) Biz logic - 대출이자계산, 신용등급계산
- **DAO** : Data Access Logic을 포함하는 객체
  - **@Repository**
- **VO** : Value Object 값을 저장하는 객체



- **@Repository** , **@Service** 어노테이션이 사용된 클래스들을 `spring-beans.xml` 에 추가

  ```xml
  <context:component-scan base-packages="myspring.user"/>
  ```

  



### 2.3) Layered Architecture

![layered](https://user-images.githubusercontent.com/58682321/79190855-4357a900-7e60-11ea-90bc-8fe560c961ac.PNG)

- 기존에는 `UserDaoImpl`에서 `SqlSession` 으로 `UserMapper`를 거치지 않고 의존했다.



- **Mapper 설정 하기**

  ```xml
  	<!-- Mapper 설정 -->
  	<!-- 아래와 같이 설정하면 Mapper 인터페이스가 추가 될 때 마다 계속 Mapper 인터페이스 이름을 설정 해주어야 한다. -->
  	<bean id="userMapper"
  		class="org.mybatis.spring.mapper.MapperFactoryBean">
  		<property name="mapperInterface"
  			value="myspring.user.dao.mapper.UserMapper" />
  		<property name="sqlSessionTemplate" ref="sqlSession" />
  	</bean>
  ```

  - `spring_beans.xml` 설정 파일에서 위와 같이 **Mapper**를 설정함으로써, `UserMapper` 인터페이스가 `SqlSession` 인터페이스를 참조하도록 설정한다.
  - 하지만, 이렇게 사용할 경우, **Mapper 인터페이스가 추가될 때 마다 계속해서 Mapper 인터페이스 이름을 설정해야하는 번거로움**이 발생한다.



- **참조 관계**

  -  `UserDaoImpMapper` →   `UserMapper` →  `SqlSession`  참조

    ```java
    @Repository("userDao")
    //Data Access Layer = @Repository
    public class UserDaoImplMapper implements UserDao {
    	@Autowired
    	private UserMapper userMapper;	
    ```

  - `UserDaoImpMapper`는 `UserDao` 인터페이스 참조





#### ※ VO.java , sql과의 관계

- **VO 클래스에서는 sql에서 선언한 컬럼 명과 동일하게 설정하지 않는다.** 즉, VO 클래스 내에서 독립적으로 변수를 선언한다.
  - sql을 참조하는 방식으로 하게 된다면, sql 내용의 변경 사항이 있을 시에 VO 클래스에서도 전부 동기화 시켜줘야 하는 번거로움이 발생
- **1:1 은 참조 관계** : `<association> `
- **1:N 은 Collection**으로 나타낼 수 있다.



- **resultType** : **컬럼명**과 **VO 클래스의 getter & setter 명**이 **동일**할 때 사용
- **resultMap** : **컬럼명**과 **VO 클래스의 getter & setter 명**이 **다를 때** 사용.  따라서 **수동으로 매핑**을 해줘야 한다.





## 3. 정리



#### 1. Mapper 인터페이스의 사용

: Type Safe 하게 Query 호출하기



- `User`는 `myspring.user.vo.UserVO` 클래스를 줄여쓴 alias 문자열이다. VO에 대한 aliasing 설정은 `SqlMapConfig.xml(MyBatis Config xml)`에 설정하였음.

  ```xml
  <mapper namespace="userNS"> 
     <select id="selectUserById" parameterType="string" resultType="User">
  	<!--  setter : 컬럼명과 setter method 명이 일치하므로 -->
  	select * from users where userid=#{value}
     </select>
  </mapper>
  ```



- **SqlSession 인터페이스** : **Mapper XML(Sql문 포함)**에 있는 SQL을 실행(수행)

  : selectOne(), selectList(), insert(stmt, parameter),update(), delete()

  - **UserVO user =sqlSession.selectOne("userNS.selectUserById", "gildong");**

- 위의 경우 SQL Id 값이 단순 문자열이므로 Type Safe하지 않아서, 잠재적인 run time 에러를 발생시킬 수 있다.

  

- **흐름** : **Service → DAO → SqlSession → Mapper.xml**

  ```java
  @Repository("userDao")
  public class UserDaoImpl implements UserDao {
      @Autowired
      private SqlSession session;
  }
  ```

  

#### 2. Mapper 인터페이스 작성



- **Mapper 인터페이스** 내의 메서드 명은 **Mapper.xml**에 선언된 **SQL id**와 반드시 같아야 한다.

  - **UserMapper 인터페이스**

  ```java
  public interface UserMapper {
      UserVO selectUserById(String id);
  }
  ```

  - **UserMapper.xml**

  ```xml
  	<select id="selectUserById" parameterType="string" resultType="User">
  		select * from users where userid=#{value}
  	</select>
  ```

  

- **흐름 : Service → DAO → Mapper → SqlSession → Mapper.xml**

  ```java
  @Repository("userDao")
  public class UserDaoImplMapper implements UserDao {
       @Autowired
       private UserMapper userMapper;	
  }
  ```



- **Level 1**

  : `UserMapper`가 `SqlSession`을 의존하는 설정

  ```xml
  <bean id="userMapper" class="org.mybatis.spring.mapper.MapperFactoryBean">
      <property name="mapperInterface" value="myspring.user.dao.mapper.UserMapper" />
      <property name="sqlSessionTemplate" ref="sqlSession" />
  </bean>
  ```

  **※ 위와 같이 설정하면 Mapper 인터페이스가 추가될 때 마다 설정을 계속 추가해야 한다. **





- **Level 2**

  : 사용자 정의 어노테이션 선언 - **MyMapper**

  ```java
  public @interface MyMapper {
      //사용자 정의 어노테이션 선언
  }
  
  
  @MyMapper  //Mapper 인터페이스 역할을 합니다.
  public interface UserMapper {
     UserVO selectUserById(String id);
  }
  
  
  @MyMapper 
  public interface ProductMapper {
  }
  ```

  - **spring_beans.xml**

  ```xml
  <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
     <property name="basePackage" value="myspring.user.dao.mapper"/>
    <property name="annotationClass" value="myspring.user.dao.mapper.MyMapper" /> 
  </bean>
  ```

**※ Level 2 와 같은 방법(사용자 정의 어노테이션)으로 인터페이스가 추가 되어도 설정을 계속 추가해야하는 번거로움을 제거할 수 있다.**

