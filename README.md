#Value At Risk(VaR) calculator

Java application to calculate Value at Risk for single and multiple trades in a portfolio.

It takes an input CSV file with the format: Date,Value,Returns,Trade and calculates the Value at Risk using historical 
for the provided CSV file. 

I have decided to go with a Spring Application. The idea behind is that this application would be part of service,
so in order to be able to extend or integrate this service the best way would be to package the logic as a microservice.

Some things that have been ignored for the purpose of this exercise are:
-Database integration
-Requests handling(load)
-Authentication


##Prerequisites:

- Java 17 
- Apache Maven : 3.9.8
- SpringBoot: 3.3.1

##General use:

Create a folder in your workspace and navigate to it:
```
mkdir varCalculator
```
```
cd varCalculator#
```

Copy it in your local machine. 
```
git clone https://github.com/j-jinjia/var-calculator.git#
```


###Running from intellij

- From maven tab(Right top screen in Intellij), select ActiveViam -> Lifecycle -> clean, validate, compile or install.
- Create a Local configuration with :

### Running from command line

-Open a new command line terminal 
- Navigate to the location of the project
```
cd varCalculator
```
- Run:
```
mvn clean install
 ```
```
mvn spring-boot:run
```

#Tests coverage

Intellij : 
Run the tests from test classes

Command line:
```
mvn test
```

##Endpoints to test:
For single trades:
```
- curl -X POST -F "file=<pathToCSV>\single-trade.csv" "http://localhost:8080/api/var/trade?confidenceLevel=0.95"
```
For multiple trades:
```
- curl -X POST -F "file=@<pathToCSV>\portfolio.csv" "http://localhost:8080/api/var/portfolio?confidenceLevel=0.95"
```

##Limitations
- The period and data values for the trades have to be provided with clean data(filtered, transformed, etc...).
- The confidence level is provided as a parameter in the endpoint. 
- The application can run locally but requires set up, since the application is not deployed to the cloud. 
- 

##Improvements
- The application could be deployed to the cloud with a cloud provider such as AWS, Google Cloud or Heroku. However for
the purposes of this exercise I have decided to not do so. The main reason being that I was required to provide a
payment method in order to use the services.
However, having the application in the cloud means that users would be able to use the application without the need of 
setting up their machines and directly interact with the API by only providing the confidence level and CSV file.
- 
##Conclusion

The application is able to calculate Value at Risk using historical method for single trades and multiple trades using 
a CSV as an input file and returns the VaR with a confidence level provided as a parameter in the endpoint.
