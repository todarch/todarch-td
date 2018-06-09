# Todo Service

It is todo service for Todarch application.

## Maven

```shell
mvn clean package

mvn clean package -DskipTests=true
```

## Docker

- Build your own image

```shell
docker image build -t todarch/td:latest -f dockerfiles/Dockerfile .
```

- Or use [the image](https://hub.docker.com/r/todarch/td/) from public repository (when master branch is updated, it is automatically rebuilt on Docker Cloud)

```shell
docker container run -it --rm --name todarch:td -p 8081:8080 todarch/td
```

- reach [health check endpoint](http://localhost:8081/non-secured/up) at local

## Heroku

- master branch is automatically deployed [as Heroku app](https://todarch-td.herokuapp.com/non-secured/up) after each successful Jenkins build.

## Swagger Api Documentation

- [on Heroku deploy](https://todarch-td.herokuapp.com/swagger-ui.html)

**Note:** Currently, the application uses H2 in-memory database, so after each restart/redeployed database will be wiped out. Later, after some maturity, an external database is going to be used.





