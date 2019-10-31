# Fr-11 Apps@FHM

Simple appstore example which was created in the context of the lecture Software Architecture (2019) @ Hochschule München.

# Setup
The project can be built and started in two steps:

1. Build kelon as docker image
```bash
# Clone the repository
$ git clone git@github.com:Foundato/kelon.git

# Build the container
$ docker build . -t kelon:latest
```

2. Build and start all components with docker-compose
```bash
$ docker-compose up -d
```

# User interfaces
* The main application should be reachable [here](http://localhost:8080).
* There is also a [Swagger-UI](http://localhost:8080/swagger-ui.html) available.

# Development
A admin user for development is created during startup.
* Username: swa_student
* Password: swa_student

# Initial work
* Anton Saatze
* Daniel Seifert
* Prof. Dr. Gudrun Socher (professor)
