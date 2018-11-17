# Todarch Todo Service

[![CircleCI](https://circleci.com/gh/todarch/todarch-td/tree/master.svg?style=svg)](https://circleci.com/gh/todarch/todarch-td/tree/master) [![Build Status](https://www.travis-ci.com/todarch/todarch-td.svg?branch=master)](https://www.travis-ci.com/todarch/todarch-td)

It is todo service for Todarch application.

## Usage and Development

- Refer to [documentation repo](https://github.com/todarch/todarch-docs) to see how whole system works together.

## Contract Tests

- Just generate the tests from contracts if contracts are updated.

```shell
mvn spring-cloud-contract:generateTests
```

- if working on contracts locally, you have to do the following steps before generating tests:

1. grab the todarch-cdc for new contracts if working in local

```shell
cd todarch-cdc
# make changes or create new constracts
mvn clean install
```

2. change contracts mode to LOCAL, so the jar in local repo will be picked up by spring cloud

```xml
<scc.contracts.mode>LOCAL</scc.contracts.mode>
```


