# Fr-11 Apps@FHM

Simple appstore example which was created in the context of the lecture Software Architecture (2019) @ Hochschule München.

# Setup
The project can be built and started in three steps:

1. Build the frontend and bundle it inside spring
```bash
$ ./scripts/build.sh
```

2. Set environment vairables for running PostgreSQL instance
```bash
export JDBC_DATABASE_URL=<your url>
export JDBC_DATABASE_USERNAME=<your user>
export JDBC_DATABASE_PASSWORD=<your password>
```

3. Start Spring via maven
```bash
# Production mode
mvn spring-boot:run -Pprod

# Local development (H2 database is used)
mvn spring-boot:run -Pdev
```

# User interfaces
* The main application should be reachable [here](http://localhost:8080).
* There is also a [Swagger-UI](http://localhost:8080/swagger-ui.html) available.


# Initial work
* Anton Saatze
* Daniel Seifert
