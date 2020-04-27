#  AOP



**review**

```
[Spring Boot]

[property 우선순위]
15위 : resources/application.properties 파일에 저장
4위 : 커맨드 라인 아규먼트

[Runtime Environment - 개발/테스트/Staging/Production]
@Profile("prod") or ("test")
@Configuration : java Config 설정 클래스
spring.profiles.active=prod(or test)

[application.properties - @Profile]
application-prod.properties
application-test.properties

[Logging : Logging Facade(인터페이스)/ Implementation(구현체)]
facade : slf4j, commons logging
impl : logback, log4j2, Jul(Java util logging)

[Log Level에 따른 Console log 출력]
Log Level : debug, info, warm, error

[H2 / Maria DB]
spring boot jdbc 의존성 추가
: DataSource, jdbcTemplate 객체 생성
HikariDataSource 구현체 자동으로(Default)

[JPA(Java Persistence API)]
: Spring boot data jpa 의존성 추가
@Entity : 엔티티 클래스, Table와 매핑
@Id : PK
@GerneratedValue : PK 값 자동 증가(자동 설정)
@Column : 컬럼명

spring.jpa.hibernate.ddl-auto : create/create-drop/update/validate
spring.jpa.show-sql=true

[Repository 인터페이스]
public interface AccountRepository extends JpaRepository<Account, Long> {

}
AccountRepository -> JpaRepository -> CrudRepository
등록 - save(entity) : Entity
조회 - findById(Id) : Optional<T>
	- findAll() : List<T>
삭제 - deleteById(Id) : T
```



## 1. AOP 개요 및 용어

> 업무 로직(**핵심 기능**)에서 부가적인 기능을 분리.
>
> Aspect라는 모듈형태로 만들어 설계하고 개발하는 방법



#### Aspect

- **Advice + PointCut**
- 부가기능을 정의한 코드인 **Advice**와 이를 어디에 적용할 지를 결정하는 **PointCut**을 합친 개념



#### AOP 용어

**Target**

- 핵심기능(비즈니스 로직)을 담고 있는 모듈로, 부가기능을 부여할 대상

**Advice**

- **Target**에 제공할 부가기능(**로깅, 보안 , 트렌잭션**)을 담고 있는 모듈

**Join Point**

- **Advice**가 적용될 수 있는 위치
- **Target** 객체가 구현한 메서드

**PointCut**

- **Adivce**를 적용할 **Target**의 메서드를 선별하는 **정규 표현식**

**Aspect**

- **AOP의 기본 모듈**
- 싱글톤 형태의 객체로 존재

**Advisor**

- **Spring AOP에서만 사용**되는 특별한 용어, **Advice**와 동일

**Weaving**

- **PointCut** 의해서 결정된 **Target**의 **Join Point**에 부가기능(**Advice**)을 삽입하는 과정



#### Spring AOP 특징

(1) Spring은 **Proxy** 기반 **AOP**를 지원한다.

- **Target**을 감싸는 **Proxy**는 **Runtime**에 생성됨
- **Proxy**는 **Advice**를 **Target** 객체에 적용하면서 생성되는 객체이다.

(2) **Proxy**가 호출을 가로챈다.

- **Target** 객체에 대한 호출을 가로채고 전처리 Advice , 후처리 Advice 로직을 수행한다.

(3) **Spring AOP**는 메서드 **Join Point**만 지원한다.





## 2. Spring AOP 구현



#### 1) XML 기반의 POJO 클래스를 이용한 AOP 구현

```xml
<bean id="ptAdvice"
		class="myspring.aop.xml.PerformanceTraceAdvice"></bean>
<aop:config>
	<aop:aspect ref="ptAdvice">
		<aop:around method="trace"
		pointcut="execution(public * myspring.user..*(..))" />
<!-- execution([접근제한자 패턴] [타입 패턴] [이름 패턴] [메서드 명] (아규먼트) -->
	</aop:aspect>
</aop:config>
```

- **\<aop:config>**을 이용해서 **Adivce**와 **PointCut**을 설정한다.



**PerformanceTraceAdvice.java**

- 해당 **Advice**는 **Target** 객체의 메서드 실행 시간을 계산해서 출력해주는 부가기능을 제공

- **Target** 객체의 메서드 실행 전, 후의 시간을 측정하는 것이기 때문에 **Around Advice**이다.
- 구현 메서드 명 : **trace(ProceedingJoinPoint joinPoint)**



#### 2) @Aspect 어노테이션을 이용한 AOP 구현

**※ 우선, XML 설정 파일에  <aop:aspectj-autoproxy />를 설정한다.** 



- **LoggingAspect.java**

```java
@Component
@Aspect
//Advice + PointCut
public class LoggingAspect {

	protected static final Logger logger = LogManager.getLogger();

	//@Before : 전처리 어드바이스
	@Before("execution(public * myspring..*(..))")
    //(생략)

	//@AfterReturning : target의 메서드가 정상종료 되었을 때만 적용됨
	@AfterReturning(pointcut = "execution(public * myspring.user.service.*.*(..))", returning = "ret")


	//@AfterThrowing : target의 메서드가 에러가 발생 되었을 때만 적용됨
	@AfterThrowing(pointcut = "execution(* *..UserService*.*(..))", throwing = "ex")


	//@After : target의 메서드가 정상종료, 에러 발생일 때 모두 적용됨
	@After("execution(* *..*.*User(..))")


```

- **@Aspect** 어노테이션을 이용할 경우 클래스 내부에서 **Advice**와 **PointCut**을 저으이 할 수 있다.
  - **@Advice 유형(pointcut = "")**



#### ※ PointCut 표현식 문법

**execution([접근제한자 패턴] 타입패턴 [타입패턴.] 이름패턴 (타입 패턴), throws 예외패턴)**

- **접근제한자 패턴** : public, private와 같은 접근제한자, **생략가능**
- **타입 패턴** : return, void와 같은 타입 패턴
- **[타입패턴.]** : **패키지**와 **클래스**이름에 대한 패턴, **생략가능**
- **이름패턴** : **메서드 이름** 타입 패턴
- **(타입 패턴)** :  **메서드 파라미터**