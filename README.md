Shopping REST Project
=============

A RESTful Spring Boot project.

# Required tools

1. [Java 8+](https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
2. [Git 2+](https://git-scm.com/downloads)
3. [Maven 3.6+](https://maven.apache.org)
4. [Docker 19+](https://www.docker.com)


# 1. Steps to configure and run the project

Necessary steps to configure, compile, run and deploy the project.


## 1.1 Download project from GIT

`git clone https://github.com/jpergentino/shopping-rest-api.git`


## 1.2 Compile using Maven

Considering the `ROOT_FOLDER` as the directory of the project in your machine, go to the `ROOT_FOLDER` and execute the command below:

:warning: Please ensure that port `8080` is available before continue;

:warning: When running Maven, a lot of libraries will be download. Please wait for it to complete.

:bulb: To skip tests and code coverage process, just add the parameter as follows: `-DskipTests=true`.

```console
cd shopping-rest-api
mvn clean package
```



# 2. Running Application

You can run using **JAVA** or **Docker**, as follows:

## 2.1 Run using Java

Go to target folder `ROOT_FOLDER/target` and execute the command below:

:bulb: To override defaults properties, copy the `ROOT_FOLDER/src/main/resources/application.properties` to `ROOT_FOLDER` and execute the command again.

```console
cd target
java -jar shopping-1.0-SNAPSHOT.jar
```

### OR


## 2.2 Run using Docker

Considering that the Docker daemon is running, we need to create a new Docker image. 

Go to the `ROOT_FOLDER/target/eb_environment` and execute the following commands:

```console
cd eb_environment
docker build -t shopping .
```

:bulb: An alternative to create the Docker image is using `docker-maven-plugin` in `ROOT_FOLDER` - _but still need the running Docker daemon_ - running the command below:

```console
mvn clean package docker:build
```

Now, let's run the Docker image executing the command below:

```console
docker run -p 8080:8080 shopping
```

:mega: You must wait until the environment is created.





## 3. Testing the application

:mega: The [MongoDB Embedded](https://www.mongodb.com) are being used in this project. The database data is created and can be observed in class `com.store.shopping.config.MongoDBInit.java`;

:mega: The database environment is created in memory and is deleted when the application is finished.

You can test using **Automated Tests** or **Docker**, as follows:

### 3.1 Automated Tests
A set of JUnit tests was developed and can be running using Maven in folder `ROOT_FOLDER` by the following command:

```console
mvn clean test
```

### OR

### 3.2 Using [Postman](https://www.getpostman.com) or Browser
The APi mapping can be accessed by using the following URLs:

```
http://localhost:8080/user
http://localhost:8080/item
http://localhost:8080/cart
```

According to RESTful patterns/conventions, the APIs are composed by the following HTTP Methods:

- POST in /entity : Add a new entity.
- PUT in /entity : Update an existing entity (when applied)
- GET in /entity: Recover all entities.
- GET in /entity/{id}: Recover the entity with specific id.
- DELETE in /entity/id: Remove the entity with specific id.

:bulb: Using the 'GET in /entity' to recover a list of entities is the best way to know the entity JSON structure/attributes. 


### OR

### 3.3 Code Coverage result :gift:

An additional code coverage was developed. The results are available after tests in folder `ROOT_FOLDER/target/site/jacoco/index.html`.

```console
mvn clean test
```




## 4. Deploying in AWS Elastic Beanstalk

:warning: Publishing our project can lead to excessive and expensive charges from cloud providers.

Steps necessary to deploy shopping in AWS Elastic Beanstalk. To install AWS AWS EB CLI, please use this [link](https://docs.aws.amazon.com/en_pv/elasticbeanstalk/latest/dg/eb-cli3-install.html)

### 4.1 Init EB Project

Assuming you set up EB CLI tool and are in `ROOT_FOLDER`, let's create the project using the command below:

First, go to `ROOT_FOLDER/target/eb_environment`:

```console
cd target/eb_environment
```

Then, init the application using the following command:

```console

eb init shopping --platform docker-18.06.1-ce --region us-west-1
```
:bulb: If you do not have an Amazon profile configured yet, you'll be prompted for your credentials.

:bulb: To see the list of supported platforms, run the `eb platform list` command.

### 4.2 Create EB environment

After creation of application, it is necessary to create the environment using the following command:

```console
eb create shopping-spot-app --single
```

:bulb: Wait until show message `(safe to Ctrl+C)`, then you can cancel using `CTRL + C` or wait to complete;

:bulb: Use the command `eb status` to get info about the environments;
 
:bulb: It's necessary to wait for changes in the 'Health status' attribute from 'Grey' to 'Green' and 'Status' attribute from 'Launching' to 'Ready';
 
:bulb: A new file is created `ROOT_FOLDER/.elasticbeanstalk/config.yml` with the following data:

```yaml
branch-defaults:
  default:
    environment: shopping-spot-app
    group_suffix: null
global:
  application_name: shopping
  branch: null
  default_ec2_keyname: null
  default_platform: Docker 18.06.1-ce
  default_region: us-west-1
  include_git_submodules: true
  instance_profile: null
  platform_name: null
  platform_version: null
  profile: eb-cli
  repository: null
  sc: null
  workspace_type: Application
```

:bulb: To support large requests into an AWS Elastic Beanstalk application, it is necessary to configure the file `ROOT_FOLDER/src/main/resources/.ebextensions/nginx.config` with the following settings:

```yaml
files:
  /etc/nginx/conf.d/proxy.conf:
    content: |
      client_max_body_size 50M;
```

## 5. Accessing the application

Use the command `eb status` to get info about the application URL by the attribute `CNAME`.

By the way, my app has been published and can be accessed by this [link](http://shopping-spot-app.cmhr9pnrbz.us-west-1.elasticbeanstalk.com).

:warning: Please advise when finishing the test so I can destroy it. :warning: