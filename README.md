CamelSpringBootEureka
simple, plain vanilla example of camel running on springboot with eureka and service routing

This is just a small demo project using Springboot with Eureka Server and Eureka Client. The Client is using camel rout with service calls.

To use clone the project, cd into EurekaService then run ./gradlew bootRun. Then cd into Eureka Client ./gradlew bootRun

Then you can hit the local client with localhost:8889/initialInput and any String as body. For instance passin in String TOM, you will get answer of:

{ "AckingBack": "tomek" }

This branch however is using "custom" service discovery instead of relaying on the SpringBoot Version.
