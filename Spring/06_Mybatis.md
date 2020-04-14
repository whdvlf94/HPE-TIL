# MyBatis



#### Dependency 설정 - Maven Repository

- mybatis
- mybatis-spring
- spring-jdbc
- oracle-jdbc driver : `ojdbc6.jar`
  - 로컬에 있는 Driver를 사용할 때는, `<scope>`태그 안에 `system`을 입력해주어야 한다.
- apache commons DBCP
  - DataSource(Interface)
    - SimpleDriverDataSource(Spring)
    - BasicDataSource(**Apache DBCP**) - 운영 모드에서 사용 가능
      - BasicDataSource를 Bean으로 등록해야 한다.



## 1. MyBatis 란?

- 개발자가 지정한 SQL, 저장프로시저 그리고 몇가지 고급 매핑을 지원하는 퍼시스턴스 프레임워크
- **SQL을 별도의 파일로 분리해서 관리**하게 해주며, 객체와 SQL 사이의 **파라미터 매핑 작업을 자동**으로 해준다.
  - **JDBC의 경우 JAVA 코드 내에 SQL을 query 문으로 작성한다.**
- XML , 어노테이션 사용 가능
- **ORM 프레임워크**

#### ORM(Object Relational Mapping)

**: MyBatis , JPA**



**매핑 Rule**

| java      | db          |
| --------- | ----------- |
| Class(VO) | Table       |
| Object    | Row(Record) |
| Variable  | Column      |

**※ MyBatis 에서는 위의 매핑 작업을 알아서 처리해준다.**





#### MyBatis 특징

- **쉬운 접근성과 코드의 간결함**
  - XML 형태로 서술된 JDBC 코드라고 생각해도 될 만큼 JDBC의 모든 기능을 MyBatis가 대부분 제공
- **SQL문과 프로그래밍 코드의 분리**
  - SQL에 변경이 있을 때마다 자바 코드를 수정하거나 컴파일 하지 않아도 된다.
- **다양한 프로그래밍 언어로 구현 가능**
  - Java, C#, NET, Ruby





## 2. Apache commons DBCP



- **apache commons DBCP**
  - DataSource(Interface)
    - SimpleDriverDataSource(Spring)
    - BasicDataSource(**Apache DBCP**) - 운영 모드에서 사용 가능
      - BasicDataSource를 Bean으로 등록해야 한다.



- **DBCP를 사용하는 이유는?**
  - Connection 생성하는 시간을 줄임으로써 보다 효율적으로 사용하기 위해서 사용한다. 즉, Connection을 미리 Pooling 해놓고, 요청 시 바로 제공



### 2.1) XML 설정

> Spring bean을 이용한 XML 설정 방법



- **values.properties**

  ```
  db.driverClass=oracle.jdbc.OracleDriver
  db.url=jdbc:oracle:thin:@127.0.0.1:1521:xe
  db.username=scott
  db.password=tiger
  ```

**※ xml 설정 파일에 직접 입력할 수도 있지만, `values.properties` 파일을 따로 둠으로써 관리하기 편하도록 한다.**



- **spring_beans.xml**

  ```xml
  <!-- DataSource 구현체인 BasicDataSource 클래스를 Bean으로 등록 -->
  	<bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource">
  		<property name="driverClassName" value="${db.driverClass}"/>
  		<property name="url" value="${db.url}"/>
  		<property name="username" value="${db.username}"/>
  		<property name="password" value="${password}"/>
  	</bean>
  
  ```

- `DataSource` 구현체 `BasicDataSource` 클래스를 Bean으로 등록
- `<property name="driverClassName">` 설정은 JDBC 에서 `Class.forName()` 과  같다.
- **url, username, password**도 JDBC에서 String으로 선언해놓은 것과 같다.



#### MyBatis Test 

- **MyBatisTest.java**

  ```java
  package myspring.user.test;
  
  import java.sql.Connection;
  import java.sql.SQLException;
  
  import javax.sql.DataSource;
  
  import org.junit.Test;
  import org.junit.runner.RunWith;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.beans.factory.annotation.Qualifier;
  import org.springframework.test.context.ContextConfiguration;
  import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
  
  @RunWith(SpringJUnit4ClassRunner.class)
  @ContextConfiguration(locations = "classpath:config/spring_beans.xml")
  
  public class MyBatisTest {
  
  	@Autowired
  	DataSource dataSource;
  	// DataSource dataSource = new BasicDataSource();
  
  	@Test
  	public void test() {
  		try {
  			Connection con = dataSource.getConnection();
  			System.out.println(con);
  		} catch (SQLException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  	}
  
  }
  -----------------------------
  1417554340, URL=jdbc:oracle:thin:@127.0.0.1:1521:xe, UserName=SCOTT, Oracle JDBC driver
  ```

  





## 3. MyBatis-Spring 의 주요 컴포넌트

> SqlSessionFactory(객체), SqlSession(인터페이스)는 MyBatis에서 제공하는 기능



- **MyBatis 설정 파일(SqlMapConfig.xml)** 

  - **VO** 객체의 정보를 설정한다.
  - VO(Value Object) 란 : 변수 선언, 생성자, getter & setter. Java Beans, DTO(Data Transfer Object), Entity라고도 불림

  

- **SqlSessionFactoyBean**

  - MyBatis 설정 파일을 바탕으로 SqlSessionFactory를 생성한다.
  - Spring Bean으로 등록해야 한다.

  ```xml
  	<!-- SqlSessionFactoryBean 클래스를 Bean으로 등록 -->
  	<bean id="sqlSessionFactory"
  		class="org.mybatis.spring.SqlSessionFactoryBean">
  		<property name="dataSource" ref="dataSource" />
  		<property name="configLocation"
  			value="classpath:config/SqlMapConfig.xml" />
  		<property name="mapperLocations">
  
  			<list>
  				<!-- Mapper(Mapping) 파일은 SQL문 or Mapping을 설정하는 파일로서 복수 개의 파일이 존재할 수 있다 
  					따라서 <list>로 작성하여 추가해준다. -->
  				<value>classpath:config/*Mapper.xml</value>
  
  <!-- config 아래에 있는 파일들 중 Mapper.xml로 끝나는 파일들을 리스트에 추가하겠다는 의미 -->
  			</list>
  		</property>
  	</bean>
  ```



- **SqlSessionTemplate**

  - **핵심적인 역할을 하는 클래스로서 SQL 실행이나 트랜잭션 관리를 실행**한다.
    - commit(), rollback(), selctOne(), selectList() ..
  - **SqlSession 인터페이스를 구현**하며, **Thread-safe**하다.
  - **Spring Bean으로 등록**해야 한다.

  ```xml
  	<!-- SqlSessionTemplate 클래스를 Bean으로 등록 -->
  	<bean id="sqlSession"
  		class="org.mybatis.spring.SqlSessionTemplate">
  
  <!-- SqlSessionTemplate 클래스는 기본생성자가 없기 때문에 constructor-arg 를 통해 Constructor injection을 해준다.-->
  		<constructor-arg ref="sqlSessionFactory" />
  	</bean>
  ```

  

- **Mapping 파일(UserMapper.xml)**

  - **SQL문과 OR Mapping을 설정한다.**

  ```xml
  		<property name="mapperLocations">
  
  			<list>
  <!-- Mapper(Mapping) 파일은 SQL문 or Mapping을 설정하는 파일로서 복수 개의 파일이 존재할 수 있다. 따라서 <list>로 작성하여 추가해준다. -->
  
                  <value>classpath:config/*Mapper.xml</value>
  <!-- config 아래에 있는 파일들 중 Mapper.xml로 끝나는 파일들을 리스트에 추가하겠다는 의미 -->
  			</list>
  		</property>
  ```

  

- **Spring Bean**
  - **SqlSessionFactoryBean**을 Bean 등록할 때 **DataSource 정보**와 **MyBatis Config 파일정보**, **Mapping 파일의 정보**를 함께 설정한다.
  - **SqlSessionTemplate**을 **Bean으로 등록**한다.





## 4. MyBatis 실습



```java
package myspring.user.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import myspring.user.vo.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/spring_beans.xml")
public class MyBatisTest {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	SqlSession sqlSession;
	
	@Test @Ignore
	public void sql2() {
		//특정 user를 update
		UserVO updateUser = new UserVO("java", "자바2", "여2", "제주2");
		int cnt = sqlSession.update("userNS.updateUser", updateUser);
		System.out.println("update cnt " + cnt);
		
		List<UserVO> selectList = sqlSession.selectList("userNS.selectUserList");
		for (UserVO userVO : selectList) {
			System.out.println(userVO);
		}
	}
	
	@Test @Ignore
	public void sql() {
		//SqlSession의 selectOne()
		UserVO user = sqlSession.selectOne("userNS.selectUserById", "gildong");
		System.out.println(user);
		
		UserVO insertUser = new UserVO("java", "자바", "여", "제주");
		int cnt = sqlSession.insert("insertUser", insertUser);
		System.out.println("등록된 건수 : " + cnt);
	}
	
	@Test @Ignore
	public void mybatis_spring() {
		System.out.println(sqlSessionFactory.getClass().getName());
		System.out.println(sqlSession.getClass().getName());
		
	}
	
	//DataSource dataSource = new BasicDataSource();
	@Test 
	public void con() {
		try {
			Connection con = dataSource.getConnection();
			System.out.println(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

```

