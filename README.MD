 # Services
 
 #### Start Reporting service instance 1
 
 `java -jar -Dserver.port=8181 reportingservice/build/libs/reportingservice-0.0.1-SNAPSHOT.jar`
 
 #### Start Reporting service instance 2
 
 `java -jar -Dserver.port=8080 build/libs/reportingservice-0.0.1-SNAPSHOT.jar`
 
 #### Start Eureka
 
 `java -jar eureka/build/libs/eureka-0.0.1-SNAPSHOT.jar`
 
 #### Start Client
  
  `java --Dserver.port=8282 jar userclient/target/userclient-0.0.1-SNAPSHOT.jar`
  
  # How it works
  `http://localhost:8282/hi-manual`
  
  calls load-balanced the Reporting Services with Ribbon throught Eureka
