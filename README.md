This is a social networking code challenge.

## Prerequisites

To run this application, you need to install java 8 and configure properly the JAVA_HOME environment variable.

## How to run?

### Without installed maven

If you don't have installed maven, just enter to console:

```
mvnw spring-boot:run
```

### With installed maven

If you have already installed maven, type as follows:

```
mvn spring-boot:run
```

### Configure port

The application starts as defaults on port: 8080. To change port number provide additional argument to cli:

```
mvn spring-boot:run -Dport=8888
```

## Documentaton

After application starts, the documentation is hosted in the following url:

```
http://localhost:8080/swagger-ui.html
```