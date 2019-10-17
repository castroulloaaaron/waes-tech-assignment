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

3. Log review
<br/>The application creates 2 log files: access_log.log and spring.log.
    1. access_log.log: contains the user interaction with the application using the HTTP protocol
    2. spring.log: contains development internal logging information

Logic Summary
-------------
The application has 4 layers:
* Controllers: expose the communication between the user and the application
* Processors: contains the rules or business logic for each operation
* Services: contains the knowledge of how execute specialized operations (like decode, persist, validate, compare)
* Models: contains the data representation to be persisted or showed to the user

An API call follow those rules:
1. Enters using the controller
2. The controller starts a process that contains one or more process that sequentially executes each of the steps to accomplish the operation goal
    1. If the operation is success, a valid message will be return to the user
    2. If there is an error, the error message (using the default JSON format from Spring) will be returned
3. Each process knows who (the service), how an the operation(s) order to call to perform an specific task. Each processor will append the data that
the next step needs. It can also stop the processors chain using a `stop flag` if there is no need to continue with the sequence (for example, if the 
request goal is already accomplish)
4. The controller returns the response to the user (on JSON format)

<br/><br/>There are 2 main process flows([Both are defined on the configuration/ProcessorsConfiguration class](/src/main/java/com/wearewaes/techassignment/aaroncastro/scalableweb/configuration/ProcessorsConfiguration.java)):
1. Persist data (for left and right operations)
<br/> Decode -> Validation -> Persist Side
<br/>![Persist data](/documentation/diagrams/persistence.png)

2. Calculate Comparison Result
<br/> Check if result was calculated -> (if not) -> Fetch sides (left/right) -> Comparison Calculation -> Persist Result
<br/>![Comparison Result](/documentation/diagrams/calculation.png)

Testing
-------
You can use any tool you prefer (postman, curl, etc) to test the application. There is 
[a postman collection](/utilities/postman/WAES-Tech-Assignment.postman_collection.json) ready to be used that shows the 
body, headers, and URL that the application uses to expose the functionality. [Here][2] is an example of how to import a Postman Collection.

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
3. User wont send a POST message bigger than 256KB
4. Data is immutable, if the user persist a message with id x, that data cannot be override. He/She/It needs to use another id for new Data/Operation
5. Id is an String

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
[2]: https://learning.getpostman.com/docs/postman/collection_runs/using_environments_in_collection_runs/