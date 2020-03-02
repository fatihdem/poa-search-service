# Power of Attorney Search Service

This is a service that let's clients search their own detailed Power of Attorney information by using multiple microservices.
Server side is written in Kotlin where api to expose resources is in Java. Used Maven for build automation and Spring Boot to jump start Spring framework. 


### Running the application (locally):

Make sure underlying microservices are running (power-of-attorney-json-stub )

Build the entire project:

```
mvn clean install
```

Run the application

```
mvn -f poa-search-server spring-boot:run 
```

####UI
After application starts, direct you browser to https://localhost:8443/ to login

####Application port and SSL
In application.yml, you can change port of the application and disable SSL since modern browsers are not very accepting of self-signed SSL certificates

####Defined users to login
In order to add comply with authorizations I added 3 users that stubs had defined ownership of power of attorneys, they all share the same password, "pass""
- Super duper employee
- Super duper company
- Fellowship of the ring

Users are hardcoded but can be easily replaced with any authentication system that Spring supports, you can check WebSecurityConfiguration class for more info

## Notes:
### Changes on apidef.yaml from Power of Attorney service
*Added /v1/account endpoint and objects for the endpoint

*Status object for both /v1/credit-card and /v1/debit-card endpoints

*In /v1/credit-card there was a typo in one field, changed monhtly to monthly

*I, intentionally, did not make any changes in data, since I thought data is responsibility of dependency

*Skipped trivial typos that are functionally not a problem like comments or operationId in Credit Card API


#####Thanks for taking the time and assessing my assignment.
#####Hope to hear you from soon.
#####Best wishes, Fatih
 