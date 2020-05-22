# Vagrant CD

> virtualbox , vagrant Download
>
> hyper V 기능 Off



#### 

#### Vagrant up

```
C:\Users\HPE\Work\vagrant>vagrant up
```



#### Setting

> cmd 관리자 권한 실행

```
C:\Windows\system32>bcdedit /set hyperviosrlaunchtype off
```



> cmd

```
C:\Users\HPE>vagrant plugin install vagrant-vbguest
```



## 1. Jenkins Server



**Jenkins-Server에 JAVA  설치**

```
C:\Users\HPE\work\vagrant>vagrant ssh jenkins-server
[vagrant@jenkins-server ~]$sudo yum -y install java-1.8*
[vagrant@jenkins-server ~]$find /usr/lib/jvm/java-1.8* | head -n 3
/usr/lib/jvm/java-1.8.0
/usr/lib/jvm/java-1.8.0-openjdk
/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.252.b09-2.el7_8.x86_64
```



**bash_profile vi 편집기**

```
[vagrant@jenkins-server ~]$ vi ~/.bash_profile

------------------------------------------------
JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-<Java version which seen in the above output>
export JAVA_HOME
PATH=$PATH:$JAVA_HOME

shift + : => wq!

exit

vagrant ssh jenkins-server (다시 로그인)
```



### 1.1) jenkins 설치(jenkins-server)

```
[vagrant@jenkins-server ~]$ sudo yum -y install wget
[vagrant@jenkins-server ~]$ sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
[vagrant@jenkins-server ~]$ sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io.key
[vagrant@jenkins-server ~]$ sudo yum -y install jenkins
```



#### Start Jenkins

```
[vagrant@jenkins-server ~]$ sudo service jenkins start
[vagrant@jenkins-server ~]$ sudo chkconfig jenkins on
------------------------------------
localhost:18080 으로 접속

/var/lib/jenkins/secrets/initialAdminPassword 복사 후 jenkins-server 에서 비밀번호 찾기

[vagrant@jenkins-server ~]$ sudo cat /var/lib/jenkins/secrets/initialAdminPassword

패스워드 입력 후 Jenkins Server 접속 가능
```



### 1.2) Jenkins Server 접속

> localhost:18080
>
> 설정에서 계정 변경 Id : admin , pw : admin



#### ※ Server network 접속 연동하기

```
[vagrant@jenkins-server ~]$ sudo vi /etc/ssh/sshd_config
---------------------------------
PasswordAuthentication yes

[vagrant@jenkins-server ~]$ sudo systemctl restart sshd
```

- tomcat, docker server 도 위와 동일하게 설정한다.



**Server 이름으로 접속하는 방법**

```
[vagrant@jenkins-server ~]$ sudo vi /etc/hosts 에 접속하여 IP 와 이름 설정할 것
----------------------------------
127.0.0.1       jenkins-server  jenkins-server
192.168.56.12 tomcat-server tomcat-server
192.168.56.13 docker-server docker-server
```

- tomcat, docker server 도 위와 동일하게 설정





### 1.3 Jenkins Server Setting



**Jenkins 관리**

![addjava](https://user-images.githubusercontent.com/58682321/82636824-7569e100-9c3e-11ea-96a3-175288355da3.PNG)

- Jenkins server cmd에서 `echo $JAVA_HOME`을 입력해 JDK 위치를 알아낸다. 결과 값을 복사하여 JAVA_HOME에 붙여넣을 것



#### 새로운 item

![workspace](https://user-images.githubusercontent.com/58682321/82637363-92eb7a80-9c3f-11ea-8706-580d1ee2dffd.PNG)



![build](https://user-images.githubusercontent.com/58682321/82637447-c1695580-9c3f-11ea-89b4-656812ad2874.PNG)

- Build Now 기능을 통해 item을 build 할 수 있다.





## 2. Tomcat Server War 설치

> Tomcat 9 tar 링크 복사하기 - https://tomcat.apache.org/download-90.cgi



#### 설치 과정

```
[vagrant@tomcat-server]$sudo mkdir -p /usr/local/tomcat
[vagrant@tomcat-server]$cd /usr/local/tomcat
[vagrant@tomcat-server tomcat]$sudo wget https://tomcat.apache.org/download-90.cgi
[vagrant@tomcat-server tomcat]$sudo tar -xvzf apache-tomcat-9.0.35.tar.gz
[vagrant@tomcat-server tomcat]$cd apache-tomcat-9.0.35/
[vagrant@tomcat-server apache-tomcat-9.0.35]$ sudo ./bin/startup.sh
```

- `localhost:28080`에 접속하면, Apache Tomcat server가 활성화 된다.



#### Manager App 403 에러 해결하기

```
1)

[vagrant@tomcat-server apache-tomcat-9.0.35]$ sudo vi conf/tomcat-users.xml
---------------------------------------------
<role rolename="manager-gui"/>    
<role rolename="manager-script"/>  
<role rolename="manager-status"/>
<user username="tomcat" password="tomcat" roles="manager-gui,manager-script,manager-status"/>

<tomcat-users> </tomcat-users>사이에 위의 값 추가하기
---------------------------------------------


2)

[vagrant@tomcat-server ~]$ sudo vi /usr/local/tomcat/apache-tomcat-9.0.35/webapps/manager/META-INF/context.xml
---------------------------------------------
<!-- <Valve className="org.apache.catalina.valves.RemoteAddrValve" allow="127\.\d+\.\d+\.\d+|::1|0:0:0:0:0:0:0:1" /> --> 

<Context> 사이에 있는 <Value> 값 주석 처리
```

- 이후 `localhost:28080/manager`에 접속하면, 로그인 창이 뜬다. 이 때, `<tomcat-users>`에서 설정한 **username , password** 값을 입력하면 Manager APP에 접근할 수 있다.

