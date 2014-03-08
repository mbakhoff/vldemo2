
To run locally:   
mvn package && java -jar target/dependency/jetty-runner.jar --port 8080 target/*.war

For development (run server and automatically refresh assets):   
mvn jetty:run
