Scalable Web Tech Assigment
===========================

Description
-----------

Spring Boot base application that compares 2 JSON Strings encoded on Base64. The goal of the assigment is to is to show 
your coding skills and what you value in software engineering.

1. Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints
    * <host>/v1/diff/<ID>/left and <host>/v1/diff/<ID>/right
2. The provided data needs to be diff-ed and the results shall be available on a third end point
    * <host>/v1/diff/<ID>
3. The results shall provide the following info in JSON format
    * If equal return that
    * If not of equal size just return that
    * If of same size provide insight in where the diffs are, actual diffs are not needed.
        * So mainly offsets + length in the data 

Preconditions
-------------

1. [Install maven][0]
2. Install jdk 11 or up
3. (Optional) [Install Postman][1]

Common Operations
-----------------

1. Run All Unit Test using maven 
```shell script
mvn clean test
```

2. Run the application using maven
```shell script
mvn clean spring-boot:run 
```

Logic Summary
-------------
There are


Useful application urls
-----------------------

1. Swagger JSON Endpoints:
    * http://localhost:8080/v2/api-docs
    * http://localhost:8080/swagger-ui.html

2. Actuator
    * http://localhost:8080/actuator

Assumptions
-----------
1. The user will use Content-Type text/plain and will Accept application/JSON
2. The application won't require Cross-Domain Headers from the application

Possible Improvements (the nice to have list)
--------------------------------------------
1. Use a durable storage (like a data-base or NoSQL)
2. Allow customization logging configuration file (loaded using a parameter when the application starts)
3. Allow customizable configuration file (loaded using a parameter when the application starts)
4. Include Cross-Domain headers
5. Security can be include: token base or with a login page (using any secure persistence storage to authenticate/authorize)
6. An UI can be added to show the differences between JSON Objects
7. Service discovery can be include
8. The circuit breaker, throttling patterns can be implemented 
9. Extends the actuator/health endpoint to display dependencies status

[0]: https://maven.apache.org/
[1]: https://www.getpostman.com
