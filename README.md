The task

Write a simple microservice (web application) to return currency data by its ISO_4217 alphabetic code. Its core functionality
must be exposed only via REST endpoints. Display the log of all requests on one UI page.

Functional requirements

1. On application startup, once, read and persist available currencies from Wikipedia page:
https://en.wikipedia.org/wiki/ISO_4217#Active_codes
(literally parse HTML document and save data to the database from 4 columns of the “Active codes” table: Code,
Num, E, Currency)
2. Write a single endpoint which receives a 3-letter currency code as its input, validates it and returns all existing data
on this code in its response, including the 3-letter code itself (based on the data you saved from Wikipedia)
3. Log all requests to this endpoint to the database - the log must contain:
   
● date/time of the request

● entered currency code

● client’s IP address

The log entry must be saved to the database regardless of whether server produced Internal Server Error (HTTP
500) or whether the code entered by API client was invalid, in other words, even if HTTP request failed with exception
or client entered wrong code, the attempt must still be logged to the database
4. REST endpoints must receive/produce data in JSON
5. Application must also have one very basic front-end HTML page which shows a table generated from the log of
requests (data from point #3). The web page should be available on URL http://localhost:8080/log . When we refresh
the page, the table must be updated with new data from server

Non-functional requirements

Application must be built and successfully run from the command line on port 8080.
Tests are mandatory.

Technical stack

● Java 8+

● Spring Boot

● H2 In-Memory database

● Hibernate/JPA

● Gradle or Maven (Gradle is preferred)

● GIT

● You are free to use any 3rd party libraries you like, for example Jsoup for parsing

● You are free to choose any UI templating framework, e.g. JSP or Thymeleaf
