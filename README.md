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

Useful URL
----------

1. Swagger JSON Endpoints:
    * http://localhost:8080/v2/api-docs
    * http://localhost:8080/swagger-ui.html

2. Actuator
    * http://localhost:8080/actuator


Assumptions
-----------


Improvements
------------
