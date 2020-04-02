# Todarch Todo Service

[![CircleCI](https://circleci.com/gh/todarch/todarch-td/tree/master.svg?style=svg)](https://circleci.com/gh/todarch/todarch-td/tree/master) [![Build Status](https://www.travis-ci.com/todarch/todarch-td.svg?branch=master)](https://www.travis-ci.com/todarch/todarch-td)

It is todo service for Todarch application.

## Usage and Development

- Refer to [documentation repo](https://github.com/todarch/todarch-docs) to see how whole system works together.

### docker

```shell
# dockerize but do not publish
./gradlew jibDockerBuild
# export IMAGE=xyz/xyzx; ./gradlew jibDockerBuild
docker images | grep td
```

- run locally

```shell
docker images | grep td
docker run -rm -p 7003:7003 todard/td
```

