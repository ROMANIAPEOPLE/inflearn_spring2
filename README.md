<details>
  <summary>스프링 핵심 기술</summary>
  <div markdown="1">
   
  ## 스프링 핵심 기술

- 스프링이 어려운 이유는 객체지향 떄문이다.

  - 다형성의 본질 : 클라이언트를 변경하지 않고 서버의 구현 기능을 유연하게 변경할 수 있다.

    인터페이스를 구현한 객체 인스턴스를 실행 시점에 유연하게 변경할 수 있다.

  - 좋은 객체 지향 애플리케이션을 개발할 수 있도록 도와주는 프레임워크

  - 객체 지향 언어가 가진 강력한 특징을 살려내는 프레임워크

  - **역할(인터페이스)**와 **구현**을 구분해야 한다. 역할(인터페이스)가 변하면 싹 다 바꿔야할수도있다.

- ​	컴포넌트 스캔의 범위를 잘 알아야 한다.

  - 스프링부트의 컴포넌트스캔 기본 설정은 애플리케이션 실행파일의 패키지로 설정되어있다.
  - 따라서 다른 패키지의 Bean을 주입하기 위해서는   *Functional Bean* Definitions을 사용하지만, 이 방법은 애초애 xml파일의 단점(설정해야 할 것들이 너무 많음)을 다시 가져오기 때문에 사용비추

- ``ApplicationContext``를 스프링 컨테이너라고 한다.

  - ``ApplicationContext``은 인터페이스이다.
  - ``new AnnotationConfigApplicationContext``로 스프링 컨테이너를 생성하면,
  - 스프링 컨테이너는 [빈 이름]과 [빈 객체]가 담긴 컨테이너를 생성한다.

- 스프링 빈을 조회할때, 부모 타입으로 조회하면 자식 타입도 모두 함께 조회된다.

  - ``Object`` 타입으로 빈을 조회하면, 모든 스프링 빈이 조회된다.

- ``BeanFactory`` 

  - 스프링 컨테이너 최상위 인터페이스
  - 스프링 빈을 관리하고 조회하는 역할을 담당한다.
  - ``getBean()``을 제공한다.

- ``ApplicationContext``

  - BeanFactory 기능을 모두 상속받아서 제공한다.

  - 그럼 BeanFactory 쓰면 되지 왜 이거쓰냐!

    - 애플리케이션을 개발할때는 빈을 관리하고 조회하는 기능은 물론, 수 많은 부가기능 필요

    - 즉 ApplicationContext는 BeanFactory 외에도 여러가지 인터페이스를 상속받는다.

      1. MessageSouce : 메시지 소스를 활용한 국제화 기능

         ->한구겡서 들어오면 한국어로, 영어권에서 들어오면 영어로 출력

      2. EnvironmentCapable : 환경변수

         ->로컬 / 개발/ 운영 등을 구분해서 처리

      3. ApplicationEventPublisher : 애플리케이션 이벤트

         ->이벤트를 발해앟고 구독하는 모델을 편리하게 지원

      4. ResourceLoader : 편리한 리소스 조회

         ->파일,클래스패스,외부 등에서 리소스를 편리하게 조회

  - BeanFactory와 ApplicationContext 모두 [스프링 컨테이너] 라고 부른다.

- ``BeanDefinition`` : 스프링은 어떻게 이런 다양한 설정 형식을 지원하는 것일까?

  - XML 을 읽어서 ``BeanDefinition`` 을 만들면 된다.
  - 자바 코드를 읽어서 ``BeanDefinition`` 을 만들면 된다.
  - 스프링 컨테이너는 XML인지 자바코드인지 알빠가 아니다. 오직 ``BeanDefinition`` 만 알면 된다.
  - 즉 스프링 컨테이너는 ``BeanDefinition`` 에만 의존한다. (XML인지, 자바코드인지 상관X)
    - 즉 구현체에 의존하지 않고 추상화에만 의존하고 있다.

- 스프링이 싱클톤 컨테이너인 이유는?

  - 스프링 없이 순수한 DI 컨테이너인 AppConfig는 요청 할 때 마다 객체를 새로 생성한다.
  - 고객 트래픽이 초당 100이나오면 초당100개 객체가 생성되고 소멸된다 ->심각한 메모리 낭비
  - 해결방안은 해당 객체가 딱 1개만 생성되고, 공유하도록 설계하면 된다 -> 싱글톤 패턴

- 싱글톤 패턴

  - 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴
  - 생성자를 private로 선언해서 new 키워드를 사용하지 못하도록 막아야 한다.
  - 인스턴스를 get해주는 방식으로, 생성된 인스턴스가 !=null이면 기존 인스턴스를 리턴하는식.

  ```java
  public class SingletonService {
      private static final SingletonService instance = new SingletonService();
      
      private SingletonService(){
          
      }
      
      public static SingletonService getInstance() {
          return instance;
      }
      
  }
  ```

  - 호출할때마다 같은 인스턴스 객체를 리턴하게 된다.
  - 결론적으로, 스프링은 자동으로 싱글톤을 적용하기 때문에 직접 구현할 필요는 없다.

  #### 싱글톤 패턴의 장점 / 문제점

   싱글톤 패턴을 적용하면 고객의 요청이 올 때 마다 객체를 생성하는 것이 아니라, 이미 만들어진 객체를 공유해서 효율적으로 재사용 할 수 있다. 

  하지만 여러가지 문제점이 존재한다.

  - 싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
  - 의존관계상 클라이언트가 구체 클래스에 의존한다 -> DIP위반
  - 클라이언트가 구체 클래스에 의존해서 OCP원칙을 위한할 가능성이 높다.
  - 테스트가 어렵다.
  - 내부 속성을 변경하거나 초기화 하기 어렵다.
  - private 생성자로, 자식 클래스를 만들기 어렵다.
  - 안티패턴으로 불리기도 한다.

 **하지만, 스프링 프레임워크는 이러한 싱글톤의 문제점을 전부 해결하고 장점만을 사용한다.** 

 **싱글톤 컨테이너**

 스프링 컨테이너는 싱글톤 패턴의 문제점을 해결하면서 객체 인스턴스를 싱글톤(1개만 생성)으로 관리한다. 지금까지 우리가 학습만 스프링 빈이 바로 싱글톤으로 관리되는 빈이다.

- 스프링 컨테이너는 싱글턴 패턴을 적용하지 않아도, 객체 인스턴스를 싱글톤으로 관리한다.
- 스프링 컨테이너가 싱글톤 컨테이너 역할을 한다. 이렇게 싱글톤 객체를 생성하고 관리하는 기능을 싱글톤 레지스트리라 한다.
- 스프링 컨테이너의 이런 기능 덕분에 싱글턴 패턴의 모든 닩머을 해결하면서 객체를 싱글톤으로 유지할 수 있다.
  - 싱글톤 패턴을 위한 지저분한 코드가 들어가지 않아도 된다.
  - DIP,OCP,테스트private생성자로 부터 자유롭게 싱글톤을 사용할 수 있다.



#### 싱글톤 방식의 주의점

- 싱글톤 패턴이든, 스프링 같은 싱글톤 컨테이너를 사용하든, 객체 인스턴스를 하나만 생성해서 공유하는 싱글톤 방식은 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 싱글톤 객체는 **상태를 유지(stateful)하게 설계하면 안된다.**

- 무상태(stateless)로 설계해야 한다!

  - 특정 클라이언트에 의존적인 필드가 있으면 안된다.
  - 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안된다.
  - 가급적 읽기만 가능해야 한다.
  - 필드 대신에 자바에서 공유되지 않는 지역변수,파라미터, ThreadLocal등을 사용해야 한다.

- <u>**스프링 빈의 필드에 공유 값을 설정하면 정말 큰 장애가 발생할 수 있다.!**</u>

  ```java
  	@Test
      void statefulServiceSingleton() {
          ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
          StatefulService statefulService1 = ac.getBean(StatefulService.class);
          StatefulService statefulService2 = ac.getBean(StatefulService.class);
  
          //Thread A : A사용자 10000원 주문
          statefulService1.order("userA", 10000);
          //Thread B : B사용자 20000원 주문
          statefulService2.order("userB", 20000);
  
          //ThreadA : 사용자A 주문 금액 조회
          int price = statefulService1.getPrice();
  
          
          assertThat(price).isEqualTo(10000); // 테스트 실패 !
  ```

  **공유 필드는 항상 조심해야한다. 스프링 빈은 항상 무상태로 설계하자.**

### @Configuration과 싱글톤 주의할 점

```java
@Configuration
public class AppConfig {
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }
    
    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }
    
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepsository();
    }
}
```

이 코드의 로직을 살펴보자.

1. memberService 빈을 만드는 코드는 memberRepository() 를 호출한다.

   즉 memberRepository() 는 new MemoryMemberRepository()를 호출한다.

2. orderService 빈을 만드는 코드 또한 memberRepository()를 호출하고 같은 객체를 리턴한다.

   **1,2번 과정을 보면 new MemoryMemberRepository()가 두번 생성된다. 따라서 싱글톤이 깨지는 것 처럼 보일수도 있다.**

   

   ### 스프링 컨테이너는 위 문제를 어떻게 해결 할까?

   스프링 컨테이너는 싱글톤 레지스트리다. 따라서 스프링 빈이 싱글톤이 되도록 보장해줘야 한다.

   하지만 스프링이 자바 코드까지 어떻게 하기는 어렵다. 위 자바 코드를 보면 분명히 MemomryMemberRepository()는 두번 생성되어야 하는데 한번만 호출된다.

   그 이유는 바로 **@Configuration과 바이트코드 조작에 있다.**

   ```java
   @Test
   void configurationDeep() {
       ApplicationContext ac = new AnnotationConfigApplicationContext(Appconfig.class);
       
       AppConfig bean = ac.getBean(AppConfig.class);
       
       System.out.println("bean = " + bean.getClass());
       //출력 : bean = class  hello.core.AppConfig$$EnhancerBySpringCGLIB$$bd479d70
   }
   ```

   AppConfig 자체도 스프링 빈이다.

   그런데 이 스프링 빈을 출력해보면 AppConfig 뒤에 $$으로 시작하는 이상한 문자들이 붙는다.

   이것이 바로 스프링이 CGLIB 라는 바이트조작 라이브러리를 사용해서 AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들고, 그 임의의 클래스를 Bean 으로 등록한 것이다.

   즉 이 임의의 클래스가 바로 싱글톤을 보장되도록 해준다.

   

   **@CGLIB 예상 코드**

   ```java
   @Bean
   public MemberRepository memberRepository() {
       if(memoryMemberRepository가 이미 스프링 컨테이너에 등록되어 있다면??)
           return 스프링 컨테이너에 찾아서 반환;
       else { //스프링 컨테이너에 없으면?
           기존 로직을 호출해서 MemoryMemberRepsotiroy를 생성하고 스프링 컨테이너에 등록
             return 반환;
       }
   }
   ```

   - @Bean이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환하고, 스프링 빈이 없으면 생성해서 스프링 빈으로 등록하고 반환하는 코드가 동적으로 만들어진다.
   - 덕분에 싱글톤이 보장되는 것이다.
   - 만약 @Configuration을 붙히지 않고 @Bean만 붙혀서 스프링 컨테이너에 빈으로 등록할 경우  스프링 빈으로는 등록이 되지만 **싱글톤은 보장되지 않는다.**



#### ComponentScan

SpringBoot를 사용하면 ComponetScan 어노테이션 자체를 사용할 일이 거의 없다.

(커스텀 수정, Filter 정의를 할 경우가 아니라면) 

```
@SpringBootApplication
```

위 어노테이션 자체에 @ComponentScan이 붙어있기 때문이다.





### 다양한 의존관계 주입 방법

- 생성자 주입

  ComponentScan을 할 때 @Component 어노테이션이 붙은것들을 스프링 컨테이너에 빈으로 등록한다. 빈을 등록하는것은 객체를 생성하는 것과 같기 때문에 생성자가 호출되고, 이 때 @Autowired가 붙어있다면 스프링 컨테이너에서 그에 맞는 스프링 빈을 꺼내서 DI(의존성 주입) 해준다. 

  - 불변 : 생성자 호출 시점에 딱 1번만 호출된다. 
  - 필수 : 기본생성자가 없기떄문에 필수적으로 의존관계 주입을 해줘야하므로 NPE를 방지한다.

- Setter 주입

  Setter메소드가 호출될때 @Autowired가 있으면 그 Bean을 주입한다.

  - 불변X : setter는 언제든지 호출 가능하기 때문에 불변이 아니다.
  - 주입할 Bean이 많아지면 개발자의 실수로 인한 오타등등 사이드이펙트가 발생가능

- 필드 주입

  필드에 @Autowired 붙혀주면 끝.

  - 스프링 없이 테스트가 불가능하다.
  - 즉 DI 프레임워크가 없으면 아무것도 할 수 없다 (순수 자바 테스트 불가능)
  - 테스트코드(SpringBootTest)에서만 사용하자.


### 스프링 빈이 두개 이상 있을때 해결방법

```java
private final DiscountPolicy discountPolicy;
	 
	@Autowired
	public OrderServiceImpl(DiscountPolicy 	discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
```

위 코드는 DiscountPolicy라는 인터페이스에 의존성을 주입하는 과정이다.

만약 아래 코드처럼 DiscountPolicy를 구현한 구현체가 2개  모두 스프링 Bean에 등록되어 있다면?

```java
@Component
public class FixDiscountPolicy implements DiscountPolicy {}

@Component
public class RateDiscountPolicy implements DiscountPolicy {}
```

 이렇게 2개의 빈이 등록되어있으면 오류가 발생한다.



##### 해결방법

- @Autowired 필드 명 매칭

  ``@Autowired``는 타입 매칭을 시도하고, 이때 여러 빈이 있으면 필드 이름, 파라미터 이름으로 빈 이름을 추가 매칭한다.

  ```java
  //기존 코드
  @Autowired
  private DiscountPolicy discountPolicy
      
  //수정 코드
  @Autowired
  private DiscountPolicy rateDiscountPolicy
  ```

  필드명이 ``rateDiscountPolicy`` 이므로 rateDiscountPlicy가 주입된다.

- @Qaulifier 사용

  ``@Qaulifier``에 이름을 설정해서 사용할 수 있다.

- @Primary 사용

  ``@Primary`` 가 붙으면 우선순위가 최상위로 잡힌다.



​	**``@Primary`` , ``@Qualifier`` 활용하기**

​	코드에서 자주 사용하는 메인 데이터베이스의 커넥션을 스프링 빈이 있고, 코드에서 특별한 기능으로 가끔씩 서브 데이터베이스의 커넥션을 흭득하는 스프링 빈이 있다고 생각해보자.  메인 데이터베이스의 커넥션을 흭득하는 스프링 빈에는 ``@Primary``를 적용시키고, 서브 데이터베이스는 ``@Qualifier``를 적용시켜서 명시적으로 흭득하는 방식으로 사용하면 코드를 깔끔하게 유지할 수 있다.



#### 자동 주입이 절대적으로 편리한데 언제 수동 빈 수입을 사용할까?

애플리케이션은 크게 [업무 로직] 과 [기술 지원 로직]으로 나뉜다.

- 업무 로직 빈 : 웹을 지원하는 컨트롤러, 핵심 비즈니스 로직이 있는 서비스, 데이터 계층의 로직을 처리하는 리포지토리등이 모두 업무 로직이다. 보통 비즈니스 요구사항을 개발할 때 추가되거나 변경된다.
- 기술 지원 빈 : 기술적인 문제나 공통 관심사(AOP)를 처리할 때 주로 사용된다. 데이터베이스 연결이나, 공통 로그 처리 처럼 업무 로직을 지원하기 위한 하부 기술이나 공통 기술들이다.

**업무 로직**은 숫자도 매우 많고, 한번 개발해야 하면 **컨트롤러, 서비스 , 리포지토리 ** 처럼 어느정도 유사한 패턴이 있다. 이런 경우 자동 주입 기능을 적극적으로 사용하는 것이 좋다. 어떤 곳에서 문제가 발생했는지 명확하게 파악하기 쉽기 때문이다.



**기술 지원 로직**은 업무 로직에 비교해서 그 수가 매우 적고, 애플리케이션 전 범위에 광범위한 영향을 미친다. 그리고 업무 로직은 문제가 발생할 경우 어디가 문제인지 명확하게 들어나지만, 기술 지원 로직은 적용이 잘 되고 있는지 아닌지 조차 파악하기 어려운 경우가 많다. 그래서 이런 기술 지원 로직들은 가급적 수동 빈 등록을 사용해서 명확하게 들어내는 것이 좋다.



### 빈 생명주기 콜백 시작

스프링 빈은 간단하게 [**객체생성** -> **의존관계 주입**] 의 라이프사이클을 가진다.

너무나도 당연한 이야기지만, 스프링 빈은 객체를 새엇앟고 의존관계 주입이 끝난 다음에야 필요한 데이터를 사용할 수 있는 준비가 완료된다.

따라서 초기화 작업은 의존관계 주입이 모두 완료되고 난 다음에야 호출된다. 

그런데 개발자가 의존관계 주입이 완료된 시점을 어떻게 알 수 있을까?

* **스프링은 의존관계 주입이 완료되면 스프링 빈에게 콜백 메서드를 통해서 초기화 시점을 알려주는 다양한 기능을 제공** 한다.

* 또한 **스프링 컨테이너가 종료되기 직전에 소멸 콜백**을 준다.

* 객체 생성과 초기화를 분리하다

  ```java
      public NetworkClient() {
          System.out.println("생성자 호출 , url = " + url);
          connect();
          call("초기화 연결 메시지");
      }
  ```

  위 코드를 보면 생성자에서 connect() 메소드와 call() 메소드를 호출하는 작업까지 하고 있다.

  생성자는 필수 정보(파라미터)를 입력받고, 메모리를 할당해서 객체를 생성하는 책임을 가진다. 반면에 초기화는 이렇게 생성된 값들을 활용해서 외부 커넥션에 연결하는 무거운 동작을 수행한다.

  따라서 생성자 안에서 무거운 초기화 작업을 함께 하는 것 보다는 객체를 생성하는 부분과 초기화 하는 부분을 명확하게 나누는 것이 유지보수 관점에서 좋다. 물론 초기화 값들이 매우 단순하다면 생성자에서 처리해도 OK



1. ``InitializingBean`` 과 ``DisposableBean`` 인터페이스를 구현해서 확인하기

   ```java
    @Override
       public void destroy() throws Exception {
           System.out.println("NetworkClient.destroy");
           disconnect();
       }
   
     @Override
      public void afterPropertiesSet() throws Exception {
           System.out.println("NetworkClient.afterPropertiesSet");
           connect();
           call("초기화 연결 메시지");
       }
   ```

   - 스프링 전용 인터페이스로, 스프링에서만 사용 가능하다.
   - 초기화/소멸 메소드의 이름을 변경할 수 없다.
   - **거의 사용하지 않는 방법**

2.  애노테이션 ``@PostConstruct`` , ``@PreDestroy`` 사용하기

   ```java
   @PostConstruct
   public void init() {
       System.out.println("NetworkClient.init")
       connect();
       call("초기화 연결 메시지");
   }
   
   @PreDestroy
   public void close() {
       System.out.println("NetworkClinet.close");
       disconnect();
   }
   
   ```





### 빈 스코프

지금까지 우리는 스프링 빈이 컨테이너의 시작과 함께 생성되어서 스프링 컨테이너가 종료될 때 까지 유지된다고 학습했다. 이것은 스프링 빈이 기본적으로 싱글톤 스코프로 생성되기 때문이다. 스코프는 번역 그대로 빈이 존재할 수 있는 범위를 뜻한다.



1. 싱글톤 빈

   - 싱글톤 빈은 스프링 컨테이너 생성 시점에 초기화 메서드가 실행된다.

   - 싱글톤 빈은 스프링 컨테이너가 관리하기 때문에  스프링 컨테이너 종료될때 빈의 종료 메서드가 함께 실행된다.

2. 프로토타입 빈 @Scope("prototype")

   - 스프링 컨테이너에 요청할 때 마다 새로 생성된다. (싱글톤이 아니다.)
   - 스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입, 그리고 초기화까지만 관여한다.
   - 종료 메서드가 호출되지 않는다.
   - 그래서 프로토타입 빈은 프로토타입 빈을 조회한 클라이언트가 직접 관리해야 한다.(스프링 컨테이너가 관리해주지 않는다.)

**그러면 프로토타입 빈을 언제 사용할까?** 

매번 사용할 때 마다 의존관계 주입이 완료된 새로운 객체가 필요하 면 사용하면 된다. 그런데 실무에서 웹 애플리케이션을 개발해보면, 싱글톤 빈으로 대부분의 문제를 해결할 수 있기 때문에 프로토타입 빈을 직접적으로 사용하는 일은 매우 드물다.

 3. request 빈

    동시에 여러 HTTP 요청이 오면 , 정확히 어떤 요청이 남긴 로그인지 파악하기 어렵다. 

    이럴때 사용하는 것이 request 스코프이다.

    - Reuqest스코프는 HTTP 요청과 동시에 빈을 생성한다.
      - 하지만 문제점이있다. HTTP요청과 동시에 빈이 생성되기 때문에, 스프링 웹 애플리케이션을 실행했을때는 빈이 존재하지 않는 상태가 되므로 오류가 발생한다.
      - 스프링 애플리케이션을 실행하는 시점에 싱글톤 빈은 생성자를 호출해서 바로 빈 주입이 가능하지만, HTTP 스코프를 사용하면 고객의 요청이 와야만 생성할 수 있기 때문이다.
      - 싱글톤과 프로토타입을 함께 사용하는 경우에 발생하는 문제에도 같은 해결책으로 사용되는 방법이 있다.

    ```java
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    ```

    프록시모드를 클래스면 TARGET_CLASS로, 인터페이스면 INTERFACE로 설정하면 된다.

    이렇게 하면 가짜 프록시 클래스를 실제 빈이 생성될 때 까지(HTTP 요청이 올 때 까지) 빈 역할을 대신 해준다.

    이는 위에서 배웠던 CGLIB(싱글톤을 유지해주는 라이브러리) 라는 라이브러리가 내 클래스를 상속받은 가짜 프록시 객체를 만들어서 빈에 주입해주는 것이다.

    

    단지 애노테이션 설정 변경만으로 원본 객체를 프록시 객체로 대체할 수 있다. 이것이 바로 다형성과 DI 컨 테이너가 가진 큰 강점이다.
  </div>
</details>

