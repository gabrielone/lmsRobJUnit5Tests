# Leave Management System
## Simple Application For Managing Employee Leaves.

### Description
Leave Management System is a web application for managing employee leaves. This application provides all the things that a project/team needs. Through this application one can easily manage leaves. Application maintenance is easy.

### Requirements
* Java 7 or higher.
* Maven
* Postgres Database, to install on windows 10: https://www.youtube.com/watch?v=e1MwsT5FJRQ&t=808s
* Run on Tomcat 7 (you can run on later versions)
* Eclipse IDE
* SonarQube

### How to install Postgres Database  
 - download the file from: https://www.postgresql.org/download/  
 - location example: D:\Infosys\PostgreSQL  
 - set PostGres password (for example "root"), let default port 5432, and be sure you select "PostgreSQL 12 on port 5432", don't install any aditional addons, click "Cancel".  
 - test your installation: open "pgAdmin4.exe", in D:\Infosys\PostgreSQL\pgAdmin 4\bin , enter your password you entered during installation.  
 Set your environment variables on Windows:  
	- Add to your Path:   
		- D:\Infosys\PostgreSQL\bin  
		- D:\Infosys\PostgreSQL\lib  
 ## Create database and tables
  * Run pgadmin and create database named "lms" , next run the scripts in ./resource folder  
  * You can find the file **app-DB-Script.sql**  in **resources** folder.
 ```c
 psql -U postgres -d lms -a -f app-DB-Script.sql  
 psql -U postgres -d lms -a -f app-DB-Inserts.sql
 ```
### How To Run
* Import as Maven Project in Eclipse and update the project
* Build maven project: "clean generate-sources install"
* make sure you have the correct credentials into : application.properties for the Database.
* Default username is `manager@email.com` and password is `123456`.
* visit localhost:8080/leave-management-system

* or, you can run with employee credentials: "employee@email.com", password: "123456"  

### How to setup your SonarQube  
```
1. Make sure you have maven installed:
	- open command line and enter: "mvn -version" 
	if not follow the steps, and remember to add Environment Variables: http://techieroop.com/how-to-install-maven-on-windows-10/  
	
2. Download SonarQube Community Edition   
https://www.sonarqube.org/downloads/  
unzip it, let's say in C:\sonarqube or /opt/sonarqube.  
mine is: C:\sonarqube  

3. Check if it's working:  
tart-> C:\sonarqube\bin\windows-x86-64\StartSonar.bat  
Log in to http://localhost:9000 with System Administrator credentials (login=admin, password=admin).  

4. Integrate Sonar with your application:  
a) Add Sonar plugin and profile in your pom.xm (if not exist)  

	<profiles>
	        <profile>
	            <id>sonar</id>
	            <activation>
	                <activeByDefault>true</activeByDefault>
	            </activation>
	            <properties>
	                <!-- Optional URL to server. Default value is http://localhost:9000 -->
	                <sonar.host.url>
	                  http://localhost:9000
	                </sonar.host.url>
	            </properties>
	        </profile>
	     </profiles>
	     
	     
b) Add Maven Plugin (if not exist) in your project pom.xml  

<pluginManagement>
    <plugins>
      <plugin>
        <groupId>org.sonarsource.scanner.maven</groupId>
        <artifactId>sonar-maven-plugin</artifactId>
        <version>3.6.0.1398</version>
      </plugin>
    </plugins>
  </pluginManagement>  
  
c) Access in your browser: localhost:9000 (check if it's working)
and login with default credentials:
login = admin
password = admin

More information: https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-maven/

5. How to use Sonar: 
 	- go to your project file folder D:\Infosys\Project\Travel Project\Spring Project\leave-management\internal-portal 
	- open command line here, " mvn package sonar:sonar " 
	- you should now see your project into: http://localhost:9000/projects  
	
	
6. How to integrate Sonar in Eclipse:
	- Go into Help -> Eclipse MarketPlace -> install SonarLint
	- start the Sonar server: C:\sonarqube\bin\windows-x86-64\StartSonar.bat  
	- follow the steps like in this video to generate reports: https://www.youtube.com/watch?v=joVhhMdxMr8  
```
## Test email request to your manager
```
email: infosysleavemanagement@gmail.com  
password: Inf@sys2020  

-  you need to set permisions in your gmail account : https://myaccount.google.com/lesssecureapps  
Manage Google Account -> Security -> Less secure app access -> Turn on  

- if you want to change the credentials :
com/lms/controllers/LeaveManageController.java -> change credentials in the method submitApplyLeave  



```
## Troubleshooting  

```
1. Error creating bean with name 'entityManagerFactory'  
org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaAutoConfiguration.class]: Invocation of init method failed; nested exception is org.hibernate.service.spi.ServiceException: Unable to create requested service [org.hibernate.engine.jdbc.env.spi.JdbcEnvironment]  

Posible causes:  
 a) you are not connected to pgadmin , or you have to restart the service:
 	- in Windows, go to: Services (run as administrator) -> search for "postgresql-x64-12" and restart  
	- start pgadmin and make sure you are connected  
	
	- if you have this ERROR when you try to start the service: The postgresql-x64-12 - PostgreSQL Server 12 service on Local
		Computer started and then stopped. Some Services stop automatically, if they are not in use by other services or programs.
		  How to solve:  
		- Task Manager(run as administrator) -> PostgreSQL Server -> end task  
		- now try to start the server again  

- now try to start the server again
 b) yor password is wrong in: src/main/resources/application.properties.txt, in variable "spring.datasource.password"  
 
 2. ERROR with port 8080
 The Tomcat connector configured to listen on port 8080 failed to start. The port may already be in use or the connector may be misconfigured.
 
 Solution 1:
 	- you have to close the process  
	- go into Command Prompt and type the following:  
	netstat -ano | findstr :8080
	taskkill /PID <type-port-pid> /F  
	

3. JUnit Error: java.lang.NoClassDefFoundError: org/junit/platform/commons/PreconditionViolationException when trying run junit5 test with maven  

Solution :  
		1. If you're using spring-boot-starter-parent, you can upgrade it to 2.2.0.RELEASE  
		2. If you use spring-boot-starter-parent (or spring-boot-dependencies), you can define a property to update just JUnit: <junit-jupiter.version>5.5.2</junit-jupiter.version>  
		2. If you're having this issue just in Eclipse, you can update it and add the JUnit 5 library to the Java Build Path (Project > Java Build Path > Libraries > Add Library > JUnit > JUnit 5 > Finish)  
```

## Application Overview
### Login Page (Initial Opening Page)
![Login Page](./resources/screenshots/Screenshot_2018-11-12%20Leave%20Management%20System.png)

### Home Page (Options provided for data variation)
![Home Page](./resources/screenshots/Screenshot_2018-11-12%20Home.png)
